package com.example.yeogiwa.openapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalResponseBodyDto<T> {
    private FestivalItemsDto<T> items;
    private int numOfRows;
    private int pageNo;
    private int totalCount;
}
