package com.example.yeogiwa.openapi.business.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessResponse {
    private String status_code;
    private Integer match_cnt;
    private Integer request_cnt;
    private List<BusinessStatus> data;
}
