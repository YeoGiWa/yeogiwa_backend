package com.example.yeogiwa.openapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalInfoDto {
    private Long contentid;
    private Integer contenttypeid;
    private String serialnum;
    private String infoname;
    private String infotext;
    private String fldgubun;
}