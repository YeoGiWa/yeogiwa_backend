package com.example.yeogiwa.controller;

import com.example.yeogiwa.service.PromotedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/promoted")
@ResponseBody
@RequiredArgsConstructor
public class PromotedController {
    private final PromotedService promotedService;
}
