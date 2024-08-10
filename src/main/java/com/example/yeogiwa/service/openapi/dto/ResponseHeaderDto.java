package com.example.yeogiwa.service.openapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseHeaderDto {
    private String resultCode;
    private String resultMsg;
}
