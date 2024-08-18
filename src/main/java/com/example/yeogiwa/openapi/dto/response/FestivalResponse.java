package com.example.yeogiwa.openapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FestivalResponse<T> {
    private FestivalResponseDto<T> response;
}
