package com.example.yeogiwa.openapi.festival.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalImageDto {
    private Long contentid;
    private String originimgurl;
    private String imgname;
    private String smallimageurl;
    private String cpyrhtDivCd;
    private String serialnum;
}