package com.demo.quartz.controller;

import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import com.demo.quartz.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quartz")
public class QuartzController {

    private final JobService jobService;

    @PostMapping("/save")
    public JobResponseDto scheduleJob(@RequestBody JobRequestDto jobRequest) {
       return jobService.save(jobRequest);
    }

    @GetMapping(value = "/info")
    public String info (Principal principal){
        return principal.getName();
    }



}
