package com.example.yeogiwa.openapi.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDto {
    private String addr1;
    private String addr2;
    private String booktour;
    private String cat1;
    private String cat2;
    private String cat3;
    private Long contentid;
    private String contenttypeid;
    private String createdtime;
    private Double dist;
    private String eventstartdate;
    private String eventenddate;
    private String firstimage;
    private String firstimage2;
    private String cpyrhtDivCd;
    private Double mapx;
    private Double mapy;
    private Integer mlevel;
    private String modifiedtime;
    private String areacode;
    private String sigungucode;
    private String tel;
    private String title;
}
