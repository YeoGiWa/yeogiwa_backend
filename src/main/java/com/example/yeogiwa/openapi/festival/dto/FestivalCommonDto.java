package com.example.yeogiwa.openapi.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FestivalCommonDto {
    private String overview;
    private Long contentid;
    private Short sigungucode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String addr1;
    private String addr2;
    private Integer zipcode;
    private Double mapx;
    private Double mapy;
    private Short mlevel;
    private String cpyrhtDivCd;
    private Integer contenttypeid;
    private String booktour;
    private String createdtime;
    private String homepage;
    private String modifiedtime;
    private String tel;
    private String telname;
    private String title;
    private String firstimage;
    private String firstimage2;
    private Integer areacode;
}