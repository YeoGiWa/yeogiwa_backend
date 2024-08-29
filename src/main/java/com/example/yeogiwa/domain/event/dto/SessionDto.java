package com.example.yeogiwa.domain.event.dto;

import com.example.yeogiwa.domain.event.EventEntity;
import com.example.yeogiwa.domain.event.SessionEntity;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionDto {
    private UUID id;

    private Integer count;

    private LocalDate startDate;

    private LocalTime startTime;

    private LocalDateTime createdAt;


    public static SessionDto of(SessionEntity sessionEntity) {
        return SessionDto.builder()
                .id(sessionEntity.getId())
                .count(sessionEntity.getCount())
                .startDate(sessionEntity.getStartDate())
                .startTime(sessionEntity.getStartTime())
                .createdAt(sessionEntity.getCreatedAt())
                .build();
    }
}