package com.example.yeogiwa.service.openapi.dto.detailImage;

import com.example.yeogiwa.service.openapi.dto.ResponseHeaderDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalDetailImageResponseDto {
    private ResponseHeaderDto header;
    private FestivalDetailImageResponseBodyDto body;
}