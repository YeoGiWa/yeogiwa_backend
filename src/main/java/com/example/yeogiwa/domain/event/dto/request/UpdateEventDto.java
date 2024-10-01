package com.example.yeogiwa.domain.event.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateEventDto {
    @Schema(description = "행사 이름", example = "서울국제빵과자페스티벌")
    private String name;

    @Schema(description = "행사 장소", example = "예술의 전당")
    private String place;

    @Schema(description = "행사에서 포인트를 앰버서더에게 나누어줄 비율. 0~100 사이의 값으로 보내주세요.", example = "50")
    private int ratio;

    @Schema(description = "이벤트에 추가할 포인트", example = "1000")
    private Integer fund;
}