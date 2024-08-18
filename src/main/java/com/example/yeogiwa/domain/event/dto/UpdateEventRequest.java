package com.example.yeogiwa.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

// TODO: 추후 연관관계 추가
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventRequest {
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
}