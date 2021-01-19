package com.demo.quartz.controller;

import com.demo.quartz.dto.JobRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quartz")
public class QuartzController {


    @PostMapping("/scheduleJob")
    public void scheduleJob(@RequestBody JobRequestDto jobRequest, Principal principal) {
    }



}
