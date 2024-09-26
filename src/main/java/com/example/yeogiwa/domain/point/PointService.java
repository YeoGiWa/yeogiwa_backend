package com.example.yeogiwa.domain.point;

import com.example.yeogiwa.domain.point.dto.PointDto;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PointService {
    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        this.pointRepository = pointRepository;
    }

    public Page<PointDto> getPointList(Long userId, Pageable pageable) {
        return pointRepository.findByUser_IdOrderByCreatedAtDesc(userId, pageable)
                .map(PointDto::from);
    }

    public Long getTotalPoints(Long userId) {
        return pointRepository.findTotalPointsByUser_Id(userId);
    }
}



