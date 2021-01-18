package com.demo.quartz.config;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;

public class JobConfig implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        System.out.println("Hello" + " " + LocalDateTime.now().toString());
    }
}
