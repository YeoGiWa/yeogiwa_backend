package com.example.yeogiwa.service.host.service;

import com.example.yeogiwa.service.host.repository.HostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HostService {
    private final HostRepository hostRepository;
}
