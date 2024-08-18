package com.example.yeogiwa.domain.fund;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fund")
@ResponseBody
@RequiredArgsConstructor
public class FundController {
    private final FundService fundService;
}
