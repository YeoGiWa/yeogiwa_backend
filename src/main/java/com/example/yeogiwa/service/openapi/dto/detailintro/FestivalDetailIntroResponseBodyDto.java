package com.example.yeogiwa.service.openapi.dto.detailintro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailIntroResponseBodyDto {
    private ListFestivalDetailIntroDto items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
