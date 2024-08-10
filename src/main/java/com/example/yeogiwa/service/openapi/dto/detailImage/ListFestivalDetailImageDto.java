package com.example.yeogiwa.service.openapi.dto.detailImage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListFestivalDetailImageDto {
    private List<FestivalDetailImageDto> item;
}