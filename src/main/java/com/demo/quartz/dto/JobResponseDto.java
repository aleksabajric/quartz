package com.demo.quartz.dto;

import com.demo.quartz.domain.SchedulerJobInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor @NoArgsConstructor
public class JobResponseDto {
    private String id;
    private String name;
    private LocalDateTime dateCreated;
    private String description;

    public JobResponseDto(SchedulerJobInfo job) {
        this.id = job.getId();
        this.name = job.getJobName();
        this.dateCreated = job.getDateCreated();
        this.description = job.getDescription();
    }

}
