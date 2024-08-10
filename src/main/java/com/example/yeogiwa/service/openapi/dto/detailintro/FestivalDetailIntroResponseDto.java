package com.example.yeogiwa.service.openapi.dto.detailintro;

import com.example.yeogiwa.service.openapi.dto.ResponseHeaderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailIntroResponseDto {
    private ResponseHeaderDto header;
    private FestivalDetailIntroResponseBodyDto body;
}