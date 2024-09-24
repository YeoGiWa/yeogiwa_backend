package com.example.yeogiwa.domain.promoted;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/promoted")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "🔥 홍보 받은 앰버서더 API", description = "홍보 받은 앰버서더(promoted) 관련 API")
public class PromotedController {
    private final PromotedService promotedService;
}