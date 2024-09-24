package com.example.yeogiwa.domain.event.dto;

import com.example.yeogiwa.domain.event.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private Long id;

    private String name;

    private String place;

    private int ratio;

    private LocalDate startAt;

    private LocalDate endAt;

    private String imageUrl;

    private String region;

    private Integer totalFund;

    private LocalDateTime createdAt;

    private List<SessionDto> sessions;

//    private List<AmbassadorEntity> ambassadors = new ArrayList<>();
//
//    private List<PromotedEntity> promotes = new ArrayList<>();
//
//    private List<FundEntity> funds = new ArrayList<>();


    public static EventDto from(EventEntity event) {
        List<SessionDto> sessions = event.getSessions().stream()
            .map(SessionDto::from)
            .toList();

        return EventDto.builder()
            .id(event.getId())
            .name(event.getName())
            .place(event.getPlace())
            .ratio(event.getRatio())
            .startAt(event.getStartAt())
            .endAt(event.getEndAt())
            .imageUrl(event.getImageUrl())
            .region(event.getRegion())
            .totalFund(event.getTotalFund())
            .createdAt(event.getCreatedAt())
            .sessions(sessions)
            .build();
    }
}