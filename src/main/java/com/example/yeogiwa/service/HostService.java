package com.example.yeogiwa.service;

import com.example.yeogiwa.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostService {
    private final HostRepository hostRepository;
}
