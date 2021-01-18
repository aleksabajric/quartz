package com.demo.quartz.service;

import com.demo.quartz.dto.JobRequestDto;

public interface JobService {
    void quartz(JobRequestDto jobRequest);
}
