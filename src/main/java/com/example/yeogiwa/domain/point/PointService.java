package com.example.yeogiwa.domain.point;

import com.example.yeogiwa.auth.jwt.JwtUtil;
import com.example.yeogiwa.domain.point.dto.PointDto;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class PointService {
    private final JwtUtil jwtUtil;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public PointService(JwtUtil jwtUtil, PointRepository pointRepository, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    public Page<PointDto> getPointList(Long userId, Pageable pageable) {
        return pointRepository.findByUser_IdOrderByCreatedAtDesc(userId, pageable)
                .map(PointDto::from);
    }

    public Long getTotalPoints(Long userId) {
        return pointRepository.findTotalPointsByUser_Id(userId);
    }
}



