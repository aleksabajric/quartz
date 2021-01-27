package com.demo.quartz.controller;

import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import com.demo.quartz.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

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
    public OAuth2User info (OAuth2AuthenticationToken oAuth2AuthenticationToken){
        return oAuth2AuthenticationToken.getPrincipal();
    }



}
