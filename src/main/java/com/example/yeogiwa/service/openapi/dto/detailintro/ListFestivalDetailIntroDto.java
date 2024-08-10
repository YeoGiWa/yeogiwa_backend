package com.example.yeogiwa.service.openapi.dto.detailintro;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ListFestivalDetailIntroDto {
    private List<FestivalDetailIntroDto> item;
}