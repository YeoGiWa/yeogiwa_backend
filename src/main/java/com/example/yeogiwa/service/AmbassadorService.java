package com.example.yeogiwa.service;

import com.example.yeogiwa.repository.AmbassadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AmbassadorService {
    private final AmbassadorRepository ambassadorRepository;
}
