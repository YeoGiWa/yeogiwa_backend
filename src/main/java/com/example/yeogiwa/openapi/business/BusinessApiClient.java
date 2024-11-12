package com.example.yeogiwa.openapi.business;

import com.example.yeogiwa.openapi.business.dto.response.BusinessRequestBody;
import com.example.yeogiwa.openapi.business.dto.response.BusinessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "businessApiClient", url = "https://api.odcloud.kr/api/nts-businessman/v1")
public interface BusinessApiClient {
    @PostMapping("/status")
    BusinessResponse businessStatus(
        @RequestParam("serviceKey") String businessServiceKey,
        @RequestBody BusinessRequestBody requestBody
    );
}
