package com.example.yeogiwa.domain.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateSessionRequest {
    @Schema(description = "[필수] 회차 카운트")
    private Integer count;

    @Schema(description = "[필수] 회차의 행사 시작 날짜")
    private LocalDate startDate;

    @Schema(description = "[필수] 회차의 행사 시작 시간", type = "string", example = "12:00:00")
    private LocalTime startTime;
}