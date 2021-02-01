package com.demo.quartz.controller;

import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import com.demo.quartz.service.JobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/quartz")
public class QuartzController {

    private final JobService jobService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public List<JobResponseDto> findAll(Principal principal){
        return jobService.findAll(principal);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public JobResponseDto scheduleJob(@RequestBody JobRequestDto jobRequest, Principal principal) {
       return jobService.save(jobRequest, principal);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE)
    public JobResponseDto update(@RequestBody JobRequestDto jobRequest, Principal principal) throws Exception {
        return jobService.update(jobRequest, principal);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    public void delete(@RequestParam String jobId){
        jobService.delete(jobId);
    }
}
