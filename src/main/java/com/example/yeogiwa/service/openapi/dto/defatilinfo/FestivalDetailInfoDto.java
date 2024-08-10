package com.example.yeogiwa.service.openapi.dto.defatilinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailInfoDto {
    private String contentid;
    private String contenttypeid;
    private String serialnum;
    private String infoname;
    private String infotext;
    private String fldgubun;
}