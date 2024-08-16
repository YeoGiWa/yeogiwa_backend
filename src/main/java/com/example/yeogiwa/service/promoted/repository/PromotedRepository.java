package com.example.yeogiwa.service.promoted.repository;

import com.example.yeogiwa.service.promoted.entity.PromotedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PromotedRepository extends JpaRepository<PromotedEntity, Long> {
}
