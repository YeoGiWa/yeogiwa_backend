package com.example.yeogiwa.domain.point.dto;

import com.example.yeogiwa.domain.point.PointEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PointDto {
    private Long id;
    private String title;
    private int amount;
    private LocalDateTime createdAt;

    public static PointDto from(PointEntity pointEntity) {
        return PointDto.builder()
                .id(pointEntity.getId())
                .title(pointEntity.getTitle())
                .amount(pointEntity.getAmount())
                .createdAt(pointEntity.getCreatedAt().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .build();
    }
}
