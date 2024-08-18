package com.example.yeogiwa.domain.promoted;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotedService {
    private final PromotedRepository promotedRepository;
}
