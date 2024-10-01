package com.example.yeogiwa.domain.host.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateHostEventDto {

    private Long eventId;
    private String title;
    private Integer ratio;
}
