package com.example.yeogiwa.domain.point;

import com.example.yeogiwa.domain.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PointRepository extends JpaRepository<PointEntity, Long> {

    Page<PointEntity> findByUser_IdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Query("SELECT SUM(p.amount) FROM PointEntity p WHERE p.user = :user")
    Long findTotalPointsByUser_Id(Long userId);
}

