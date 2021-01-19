package com.demo.quartz.service.impl;

import com.demo.quartz.dao.SchedulerJobInfoDao;
import com.demo.quartz.domain.SchedulerJobInfo;
import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import com.demo.quartz.service.JobService;
import com.demo.quartz.service.QuartzSchedulerProvider;
import com.demo.quartz.service.QuartzUtil;
import com.demo.quartz.util.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final QuartzSchedulerProvider quartzSchedulerProvider;
    private final QuartzUtil quartzUtil;
    private final SchedulerJobInfoDao schedulerJobInfoDao;
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    @Override
    public Page<JobResponseDto> findAll(Predicate predicate, Pageable pageable) {
//        Page<JobResponseDto> page = schedulerJobInfoDao.findAll(predicate, pageable);
//        return new PageImpl<>(page.stream().map(JobResponseDto::new).collect(Collectors.toList()), pageable, page.getTotalElements());
    return null;
    }

    @Override
    public JobResponseDto findById(String id) {
        return null;
    }

    @Override
    public SchedulerJobInfo findOne(String id) {
        return null;
    }

    @Override
    @Transactional
    public JobResponseDto save(JobRequestDto requestDto) {
        String jobId = UUID.randomUUID().toString();
        SchedulerJobInfo job = new SchedulerJobInfo();
        job.setId(jobId);
        job.setJobName(requestDto.getName());
        job.setCronExpression(requestDto.getCron());
        job.setDescription(requestDto.getDescription());
        quartzUtil.validateCronExpression(requestDto.getCron());

        try {
            quartzSchedulerProvider.scheduleJob(jobId, requestDto.getCron(), JobResolver.class);
        } catch (SchedulerException e) {
            e.printStackTrace();
        }

        schedulerJobInfoDao.save(job);
        logger.info("New system job: {}", job.toString());
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
