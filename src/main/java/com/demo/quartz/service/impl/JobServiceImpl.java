package com.demo.quartz.service.impl;

import com.demo.quartz.dao.SchedulerJobInfoDao;
import com.demo.quartz.domain.SchedulerJobInfo;
import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import com.demo.quartz.dto.UserResponse;
import com.demo.quartz.service.JobService;
import com.demo.quartz.service.QuartzSchedulerProvider;
import com.demo.quartz.service.UserService;
import com.demo.quartz.service.helpers.QuartzUtil;
import com.demo.quartz.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
    private final QuartzSchedulerProvider quartzSchedulerProvider;
    private final QuartzUtil quartzUtil;
    private final SchedulerJobInfoDao schedulerJobInfoDao;
    private final UserService userService;

    @Override
    public List<JobResponseDto> findAll(Principal principal) {
        UserResponse user = userService.findOrFetch(principal.getName().trim());
        if (!Objects.isNull(user)){
            return schedulerJobInfoDao.findAll(user.getId()).stream().map(JobResponseDto::new).collect(Collectors.toList());
        }else {
            throw new ResourceNotFoundException(String.format("User does not exist: {}", principal.getName()));
        }

    }

    @Override
    public JobResponseDto findById(String id) {
        return new JobResponseDto(findOne(id));
    }

    @Override
    public SchedulerJobInfo findOne(String id) {
        return schedulerJobInfoDao.findById(id).orElseThrow(()->{
            throw new ResourceNotFoundException(String.format("Job with id: {} does not exist", id));
        });
    }

    @Override
    @Transactional
    public JobResponseDto save(JobRequestDto requestDto, Principal principal) {
        UserResponse user = userService.findOrFetch(principal.getName().trim());
        String jobId = UUID.randomUUID().toString();
        SchedulerJobInfo job = new SchedulerJobInfo();
        job.setId(jobId);
        job.setJobName(requestDto.getName());
        job.setCronExpression(requestDto.getCron());
        job.setDescription(requestDto.getDescription());
        job.setDateCreated(LocalDateTime.now());
        job.setUserId(user.getId());
        quartzUtil.validateCronExpression(requestDto.getCron());
        logger.info("New job: {}", job.toString());
        try {
            quartzSchedulerProvider.scheduleJob(jobId, requestDto.getCron(), JobResolver.class);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        schedulerJobInfoDao.save(job);
        return new JobResponseDto(job);
    }

    @Override
    @Transactional
    public JobResponseDto update(JobRequestDto requestDto) throws Exception {
        SchedulerJobInfo job = findOne(requestDto.getId());
        job.setJobName(requestDto.getName());
        job.setDescription(requestDto.getDescription());
        if(!job.getCronExpression().equals(requestDto.getCron())) {
            try {
                quartzUtil.validateCronExpression(requestDto.getCron());
                job.setCronExpression(requestDto.getCron());
                quartzSchedulerProvider.rescheduleJob(job.getId(), requestDto.getCron());
            } catch (SchedulerException e) {
                e.printStackTrace();
            }
        }
        schedulerJobInfoDao.save(job);
        logger.info("Job {} successfully updated.", job.getId());
        return new JobResponseDto(job);
    }

    @Override
    @Transactional
    public Boolean delete(String id) {
        if(!schedulerJobInfoDao.existsById(id)) {
            throw new ResourceNotFoundException("Job not found!");
        }
        try {
            quartzSchedulerProvider.deleteJob(id);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        schedulerJobInfoDao.deleteById(id);
        logger.info("Job {} successfully deleted.", id);
        return true;
    }
}
