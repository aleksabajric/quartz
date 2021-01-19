package com.demo.quartz.service.impl;

import lombok.RequiredArgsConstructor;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobResolver extends QuartzJobBean {

    private static final Logger logger = LoggerFactory.getLogger(JobResolver.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        var key = context.getJobDetail().getKey().getName();
        logger.info("This is a job time! Job: {}", key);
    }
}
