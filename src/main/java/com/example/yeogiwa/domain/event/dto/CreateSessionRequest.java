package com.example.yeogiwa.domain.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateSessionRequest {
    @Schema(description = "[필수] 추가할 회차의 8자리의 행사 ID", example = "25413323")
    private String eventId;

    @Schema(description = "[필수] 회차 카운트")
    private Integer count;

    @Schema(description = "[필수] 회차의 행사 시작 날짜")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "[필수] 회차의 행사 시작 시간", type = "string", example = "12:00:00")
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime startTime;
}