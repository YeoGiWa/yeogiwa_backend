package com.example.yeogiwa.domain.event.dto;

import com.example.yeogiwa.domain.event.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private Long id;
    private Long host;
    private Long upperEventId;
    private String title;
    private Integer round;
    private Integer ratio;
    private LocalDateTime createdAt;
    private Boolean isDeleted;

    public static EventDto from(EventEntity event) {
        return EventDto.builder()
            .id(event.getId())
            .host(event.getHostId())
            .upperEventId(event.getUpperEventId())
            .title(event.getTitle())
            .round(event.getRound())
            .ratio(event.getRatio())
            .createdAt(event.getCreatedAt())
            .isDeleted(event.getIsDeleted())
            .build();
    }
}