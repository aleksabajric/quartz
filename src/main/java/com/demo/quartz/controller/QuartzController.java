package com.demo.quartz.controller;

import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import com.demo.quartz.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quartz")
public class QuartzController {

    private final JobService jobService;

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public JobResponseDto scheduleJob(@RequestBody JobRequestDto jobRequest) {
       return jobService.save(jobRequest);
    }

    @GetMapping(value = "/info")
    public String info (Principal principal){
        return principal.getName();
    }



}
