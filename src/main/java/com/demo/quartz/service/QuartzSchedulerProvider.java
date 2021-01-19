package com.demo.quartz.service;

import org.quartz.Job;
import org.quartz.SchedulerException;

public interface QuartzSchedulerProvider {
    void scheduleJob (String jobId, String cronExpression, Class<? extends Job> resolver) throws SchedulerException;

    void rescheduleJob (String jobId, String newCronExpression) throws SchedulerException;

    void deleteJob (String jobId) throws SchedulerException;
}
