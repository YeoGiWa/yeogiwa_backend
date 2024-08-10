package com.example.yeogiwa.service.openapi.dto.detailcommon;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListFestivalDetailDto {
    private List<FestivalDetailDto> item;
}