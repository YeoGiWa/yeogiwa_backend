package com.example.yeogiwa.openapi.festival.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalItemsDto<T> {
    private List<T> item;
}
