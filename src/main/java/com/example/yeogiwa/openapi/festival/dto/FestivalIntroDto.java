package com.example.yeogiwa.openapi.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalIntroDto {
    private Long contentid;
    private Integer contenttypeId;
    private String agelimit;
    private String bookingplace;
    private String discountinfofestival;
    private String eventenddate;
    private String eventhomepage;
    private String eventplace;
    private String eventstartdate;
    private String festivalgrade;
    private String placeinfo;
    private String playtime;
    private String program;
    private String spendtimefestival;
    private String sponsor1;
    private String sponsor1tel;
    private String sponsor2;
    private String sponsor2tel;
    private String subevent;
    private String usetimefestival;
}