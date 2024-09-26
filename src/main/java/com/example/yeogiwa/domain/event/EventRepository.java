package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.host.HostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<EventEntity, String> {
    List<EventEntity> findAllByIdIn(List<Long> ids);

    Page<EventEntity> findAllByStartAtBetweenOrderByStartAtDesc(Pageable pageable, LocalDate startAt, LocalDate endAt);

    Page<EventEntity> findAllByStartAtBetweenAndRegionOrderByStartAtDesc(Pageable pageable, LocalDate startAt, LocalDate endAt, String region);

    List<EventEntity> findAllByHostIn(List<HostEntity> hosts);
}