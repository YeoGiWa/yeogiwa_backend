package com.example.yeogiwa.domain.admin;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@ResponseBody
@Tag(name = "✅ 어드민 API", description = "어드민 페이지 관련 API")
public class AdminController {

    @GetMapping("/")
    public String adminTest() {
        return "Admin Controller";
    }
}
