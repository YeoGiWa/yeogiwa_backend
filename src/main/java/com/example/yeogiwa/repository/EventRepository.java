package com.example.yeogiwa.repository;

import com.example.yeogiwa.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<EventEntity, Long> {
}
