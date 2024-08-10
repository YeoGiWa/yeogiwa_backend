package com.example.yeogiwa.service.openapi.dto.defatilinfo;

import com.example.yeogiwa.service.openapi.dto.ResponseHeaderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailInfoResponseDto {
    private ResponseHeaderDto header;
    private FestivalDetailInfoResponseBodyDto body;
}