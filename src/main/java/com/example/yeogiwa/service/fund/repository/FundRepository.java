package com.example.yeogiwa.service.fund.repository;

import com.example.yeogiwa.service.fund.entity.FundEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FundRepository extends JpaRepository<FundEntity, Long> {
}
