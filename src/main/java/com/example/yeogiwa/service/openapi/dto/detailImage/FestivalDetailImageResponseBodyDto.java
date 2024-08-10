package com.example.yeogiwa.service.openapi.dto.detailImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailImageResponseBodyDto {
    private ListFestivalDetailImageDto items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
