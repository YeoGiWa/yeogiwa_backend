package com.example.yeogiwa.service.fund.service;

import com.example.yeogiwa.service.fund.repository.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundService {
    private final FundRepository fundRepository;
}
