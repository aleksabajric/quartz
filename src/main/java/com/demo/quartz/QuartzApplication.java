package com.demo.quartz;

import org.quartz.SchedulerException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class QuartzApplication {

	public static void main(String[] args) throws SchedulerException {
		SpringApplication.run(QuartzApplication.class, args);
	}
}
