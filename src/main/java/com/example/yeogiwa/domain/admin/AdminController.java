package com.example.yeogiwa.domain.admin;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@ResponseBody
public class AdminController {

    @GetMapping("/")
    public String adminTest() {
        return "Admin Controller";
    }
}
