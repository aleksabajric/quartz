package com.demo.quartz.service.impl;

import com.demo.quartz.config.JobConfig;
import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.service.JobService;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {
    private static final Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);

    private final Scheduler scheduler;

    @Override
    public void quartz(JobRequestDto jobRequest) {
        try {
            scheduler.start();
            ZonedDateTime dateTime = ZonedDateTime.of(jobRequest.getDateTime(), jobRequest.getTimeZone());
            JobDetail jobDetail = buildJobDetail(jobRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException ex) {
            logger.error("Error scheduling job", ex);
        }
    }

    private JobDetail buildJobDetail(JobRequestDto jobRequest) {
        logger.info(jobRequest.getDateCreated().toString());
        return JobBuilder.newJob(JobConfig.class)
                .withIdentity(UUID.randomUUID().toString(), "jobs")
                .withDescription("Job")
                .storeDurably()
                .build();
    }

    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "job-triggers")
                .withDescription("Send Trigger")
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(CronScheduleBuilder.cronSchedule("0 0/1 * 1/1 * ? *"))
                .build();
    }
}
