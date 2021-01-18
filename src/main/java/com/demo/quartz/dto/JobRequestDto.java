package com.demo.quartz.dto;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class JobRequestDto {
    private Long id;
    private String name;
    @Builder.Default
    private LocalDateTime dateCreated = LocalDateTime.now();
    private String describe;
    @NotNull
    private LocalDateTime dateTime;
    @NotNull
    private ZoneId timeZone;
}
