package com.example.yeogiwa.repository;

import com.example.yeogiwa.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
}
