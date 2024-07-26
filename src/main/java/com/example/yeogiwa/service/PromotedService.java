package com.example.yeogiwa.service;

import com.example.yeogiwa.repository.PromotedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotedService {
    private final PromotedRepository promotedRepository;
}
