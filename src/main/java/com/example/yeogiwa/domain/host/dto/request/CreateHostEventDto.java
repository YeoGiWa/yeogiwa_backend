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
public class CreateHostEventDto {

    @Schema(example = "2541883")
    private Long eventId;
    @Schema(example = "강릉 문화유산 야행")
    private String title;
    @Schema(example = "50")
    private Integer ratio;
}
