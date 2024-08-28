package com.example.yeogiwa.domain.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateEventRequest {
    @Schema(description = "[필수] 8자리의 행사 ID", example = "25413323")
    private String id;

    @Schema(description = "[필수] 행사 이름", example = "서울국제빵과자페스티벌")
    private String name;

    @Schema(description = "[필수] 행사에서 포인트를 앰버서더에게 나누어줄 비율. 0~100 사이의 값으로 보내주세요.", example = "50")
    @Min(0)
    @Max(100)
    private int ratio;
}