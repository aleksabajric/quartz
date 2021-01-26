package com.demo.quartz.service.impl;

import com.demo.quartz.service.QuartzSchedulerProvider;
import com.demo.quartz.service.helpers.QuartzUtil;
import lombok.AllArgsConstructor;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class QuartzSchedulerProviderImpl implements QuartzSchedulerProvider {

    private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerProvider.class);

    private final Scheduler scheduler;

    private final QuartzUtil quartzUtil;

    @Override
    public void scheduleJob(String jobId, String cronExpression, Class<? extends Job> resolver) throws SchedulerException {
        scheduler.scheduleJob(quartzUtil.buildJobDetail(jobId, resolver), quartzUtil.buildCronTrigger(jobId, cronExpression));
        logger.info("New job was scheduled: (id: {}, cron: {})", jobId, cronExpression);
    }

    @Override
    public void rescheduleJob(String jobId, String newCronExpression) throws SchedulerException {
        scheduler.rescheduleJob(new TriggerKey(jobId), quartzUtil.buildCronTrigger(jobId, newCronExpression));
        logger.info("Job rescheduled: (id: {}, cron: {})", jobId, newCronExpression);
    }

    @Override
    public void deleteJob(String jobId) throws SchedulerException {
        scheduler.deleteJob(new JobKey(jobId));
        logger.info("Deleted job: {}", jobId);
    }

}
