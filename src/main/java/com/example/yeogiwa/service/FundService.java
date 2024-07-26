package com.example.yeogiwa.service;

import com.example.yeogiwa.repository.FundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundService {
    private final FundRepository fundRepository;
}
