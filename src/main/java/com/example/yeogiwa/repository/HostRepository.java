package com.example.yeogiwa.repository;

import com.example.yeogiwa.entity.HostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HostRepository extends JpaRepository<HostEntity, Long> {
}
