package com.demo.quartz.service.helpers;

import com.demo.quartz.util.InvalidCronException;
import org.quartz.*;
import org.springframework.stereotype.Component;

@Component
public class QuartzUtil {

    public void validateCronExpression (String expression) {
        if(!CronExpression.isValidExpression(expression)) {
            throw new InvalidCronException("Invalid cron expression");
        }
    }

    public JobDetail buildJobDetail (String jobId, Class<? extends Job> resolver) {
        return JobBuilder.newJob(resolver)
                .withIdentity(jobId)
                .storeDurably()
                .build();
    }

    public CronTrigger buildCronTrigger (String jobId, String cronExpression) {
        return TriggerBuilder.newTrigger()
                .withIdentity(jobId)
                .withSchedule(CronScheduleBuilder.cronSchedule(cronExpression))
                .build();
    }

}
