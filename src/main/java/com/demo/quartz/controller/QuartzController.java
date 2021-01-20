package com.demo.quartz.controller;

import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import com.demo.quartz.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quartz")
public class QuartzController {

    private final JobService jobService;

    @PostMapping("/save")
    public JobResponseDto scheduleJob(@RequestBody JobRequestDto jobRequest) {
       return jobService.save(jobRequest);
    }



}
