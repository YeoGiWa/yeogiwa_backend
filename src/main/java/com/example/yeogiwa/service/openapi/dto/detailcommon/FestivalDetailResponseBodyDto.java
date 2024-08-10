package com.example.yeogiwa.service.openapi.dto.detailcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailResponseBodyDto {
    private ListFestivalDetailDto items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
