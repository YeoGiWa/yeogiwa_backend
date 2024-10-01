package com.example.yeogiwa.domain.host.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHostRoundDto {
    @Schema(description = "행사 ID", example = "1032770")
    private Long eventId;
    @Schema(description = "행사명", example = "부산국제매직페스티벌")
    private String title;
    @Schema(description = "행사 회차", example = "2")
    private Integer round;
    @Schema(description = "포인트 비율 0~100 사이", example = "50")
    private Integer ratio;
}
