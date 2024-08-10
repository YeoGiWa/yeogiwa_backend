package com.example.yeogiwa.service.openapi.dto.defatilinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailInfoResponseBodyDto {
    private ListFestivalDetailInfoDto items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
