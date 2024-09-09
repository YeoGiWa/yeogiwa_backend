package com.example.yeogiwa.domain.fund;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fund")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "💰 펀드 API", description = "등록된 이벤트의 포인트 관련 API")
public class FundController {
    private final FundService fundService;
}
