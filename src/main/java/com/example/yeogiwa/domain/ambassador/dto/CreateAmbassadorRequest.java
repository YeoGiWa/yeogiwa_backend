package com.example.yeogiwa.domain.ambassador.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateAmbassadorRequest {
    @Schema(description = "8자리의 행사 ID", example = "25413323")
    private String eventId;
}