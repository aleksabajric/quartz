package com.demo.quartz.service;

import com.demo.quartz.config.UserDetailsDto;
import com.demo.quartz.domain.tokendata.TokenData;
import com.demo.quartz.dto.JobResponseDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;

import java.security.Principal;
import java.util.List;

import static org.junit.Assert.assertNotNull;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JobServiceTests {

    @Autowired
    private JobService jobService;

    @BeforeAll
    void setup() {
        TokenData tokenDto = new TokenData("Aleksa", "bajricaleksa4@gmail.com");
        UserDetailsDto userDetails = new UserDetailsDto(tokenDto);
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, null);
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Test
    void findAllTest() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(principal.getName());
        List<JobResponseDto> results = jobService.findAll(principal);
        assertNotNull(results);
    }

}
