package com.demo.quartz.service;

import com.demo.quartz.config.UserDetailsDto;
import com.demo.quartz.domain.tokendata.TokenData;
import com.demo.quartz.dto.JobRequestDto;
import com.demo.quartz.dto.JobResponseDto;
import com.demo.quartz.util.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.List;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class JobServiceTests {

    @Autowired
    private JobService jobService;

    @BeforeEach
    void setup() {
        TokenData tokenDto = new TokenData("Aleksa", "bajricaleksa4@gmail.com");
        UserDetailsDto userDetails = new UserDetailsDto(tokenDto);
        var authToken = new UsernamePasswordAuthenticationToken(userDetails, "test", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    @Test
    void findAllTest() {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        List<JobResponseDto> results = jobService.findAll(principal);
        assertNotNull(results);
    }

    @Test
    @Transactional
    void saveTest(){
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        JobRequestDto requestDto = JobRequestDto.builder()
                .cron("0 0/1 * 1/1 * ? *")
                .name("test")
                .description("test")
                .build();

        JobResponseDto save = jobService.save(requestDto, principal);
        assertNotNull(save);
    }

    @Test
    @Transactional
    void updateTest() throws Exception {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        JobRequestDto requestDto = JobRequestDto.builder()
                .cron("0 0/1 * 1/1 * ? *")
                .id("c5e3db74-d07c-47cc-ab67-12976e9a344d").build();
        assertThrows(ResourceNotFoundException.class, ()-> jobService.update(requestDto, principal));
    }


}
