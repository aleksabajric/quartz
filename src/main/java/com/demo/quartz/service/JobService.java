package com.demo.quartz.service;

import com.demo.quartz.domain.SchedulerJobInfo;
import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;

import java.security.Principal;
import java.util.List;

public interface JobService {
    List<JobResponseDto> findAll(Principal principal);

    JobResponseDto findById(String id);

    SchedulerJobInfo findOne(String id);

    JobResponseDto save(JobRequestDto requestDto, Principal principal);

    JobResponseDto update(JobRequestDto requestDto) throws Exception;

    Boolean delete(String id);
}
