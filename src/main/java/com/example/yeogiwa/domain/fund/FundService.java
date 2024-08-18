package com.example.yeogiwa.domain.fund;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FundService {
    private final FundRepository fundRepository;
}
