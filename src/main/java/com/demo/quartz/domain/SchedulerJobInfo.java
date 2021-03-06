package com.demo.quartz.domain;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "scheduler_job_info")
@Data
public class SchedulerJobInfo {

    @Id
    private String id;

    @Column(name = "job_name")
    private String jobName;

    private String description;

    @Column(name = "cron_expression")
    private String cronExpression;

    @Column(name = "date_created")
    private LocalDateTime dateCreated;

    @Column(name = "cron_job")
    private Boolean cronJob;

    @Column(name = "user_id")
    private Long userId;
}
