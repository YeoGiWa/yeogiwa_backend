package com.example.yeogiwa.service.openapi.dto.defatilinfo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListFestivalDetailInfoDto {
    private List<FestivalDetailInfoDto> item;
}