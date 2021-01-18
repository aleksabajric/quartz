package com.demo.quartz.domain;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table(name = "scheduler_job_info")
@Data
public class SchedulerJobInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobName;

    private String jobGroup;

    private String jobClass;

    private String cronExpression;

    private Long repeatTime;

    private Boolean cronJob;
}
