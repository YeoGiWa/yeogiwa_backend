package com.example.yeogiwa.service.openapi.dto.detailcommon;

import com.example.yeogiwa.service.openapi.dto.ResponseHeaderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailResponseDto {
    private ResponseHeaderDto header;
    private FestivalDetailResponseBodyDto body;
}