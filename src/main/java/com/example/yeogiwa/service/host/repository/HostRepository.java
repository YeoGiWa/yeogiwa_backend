package com.example.yeogiwa.service.host.repository;

import com.example.yeogiwa.service.host.entity.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
}
