package com.example.yeogiwa.domain.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EventEtc {
    private Boolean isApplicable;
    private Integer round;
}
