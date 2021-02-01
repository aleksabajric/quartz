package com.demo.quartz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class JobRequestDto {
    private String id;
    private String name;
    @Builder.Default
    private LocalDateTime dateCreated = LocalDateTime.now();
    private String description;
    private String cron;
}
