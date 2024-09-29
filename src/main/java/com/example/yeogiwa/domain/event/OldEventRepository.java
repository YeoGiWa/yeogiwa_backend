package com.example.yeogiwa.domain.event;

import com.example.yeogiwa.domain.host.HostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

//@Repository
public interface OldEventRepository {
//    List<OldEventEntity> findAllByIdIn(List<Long> ids);
//
//    Page<OldEventEntity> findAllByStartAtBetweenOrderByStartAtDesc(Pageable pageable, LocalDate startAt, LocalDate endAt);
//
//    Page<OldEventEntity> findAllByStartAtBetweenAndRegionOrderByStartAtDesc(Pageable pageable, LocalDate startAt, LocalDate endAt, String region);
//
//    List<OldEventEntity> findAllByHostIn(List<HostEntity> hosts);
}