package com.example.yeogiwa.service.point.repository;

import com.example.yeogiwa.service.point.entity.PointEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
}
