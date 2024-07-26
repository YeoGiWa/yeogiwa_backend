package com.example.yeogiwa.controller;

import com.example.yeogiwa.service.HostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/host")
@ResponseBody
@RequiredArgsConstructor
public class HostController {
    private final HostService hostService;
}
