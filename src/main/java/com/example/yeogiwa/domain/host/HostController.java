package com.example.yeogiwa.domain.host;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host")
@ResponseBody
@RequiredArgsConstructor
@Tag(name = "🦹‍ 호스트 API", description = "호스트 관련 API")
public class HostController {
    private final HostService hostService;
}
