package com.example.yeogiwa.service.openapi.dto.detailcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailDto {
    private String overview;
    private String contentid;
    private String sigungucode;
    private String cat1;
    private String cat2;
    private String cat3;
    private String addr1;
    private String addr2;
    private String zipcode;
    private String mapx;
    private String mapy;
    private String mlevel;
    private String cpyrhtDivCd;
    private String contenttypeid;
    private String booktour;
    private String createdtime;
    private String homepage;
    private String modifiedtime;
    private String tel;
    private String telname;
    private String title;
    private String firstimage;
    private String firstimage2;
    private String areacode;
}