package com.example.yeogiwa.service.promoted.service;

import com.example.yeogiwa.service.promoted.repository.PromotedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotedService {
    private final PromotedRepository promotedRepository;
}
