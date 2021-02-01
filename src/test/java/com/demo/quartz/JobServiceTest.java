package com.demo.quartz;

import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(
        classes = TestUserDetailsImpl.class
)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureMockMvc
public class JobServiceTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value = "bajricaleksa4@gmail.com", userDetailsServiceBeanName = "userDetailsService")
    public void test() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/quartz/findAll")
                .accept(MediaType.ALL))
                .andExpect(status().isOk());
    }

}
