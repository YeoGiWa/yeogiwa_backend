package com.example.yeogiwa.domain.event.dto;

import com.example.yeogiwa.domain.event.EventEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventDto {
    private Long id;

//    private HostEntity host;

    private String name;

    private String place;

    private int ratio;

    private Date createdAt;

//    private List<AmbassadorEntity> ambassadors = new ArrayList<>();
//
//    private List<PromotedEntity> promotes = new ArrayList<>();
//
//    private List<FundEntity> funds = new ArrayList<>();


    public static EventDto from(EventEntity event) {
        return EventDto.builder()
            .id(event.getId())
            .name(event.getName())
            .place(event.getPlace())
            .ratio(event.getRatio())
            .createdAt(event.getCreatedAt())
            .build();
    }
}