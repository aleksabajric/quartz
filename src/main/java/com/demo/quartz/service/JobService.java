package com.demo.quartz.service;

import com.demo.quartz.domain.SchedulerJobInfo;
import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.function.Predicate;

public interface JobService {
    Page<JobResponseDto> findAll (Predicate predicate, Pageable pageable);

    JobResponseDto findById (String id);

    SchedulerJobInfo findOne (String id);

    JobResponseDto save (JobRequestDto requestDto);

    JobResponseDto update (JobRequestDto requestDto) throws Exception;

    Boolean delete (String id);
}
