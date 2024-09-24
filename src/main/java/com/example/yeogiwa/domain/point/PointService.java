package com.example.yeogiwa.domain.point;

import com.example.yeogiwa.auth.jwt.JwtUtil;
import com.example.yeogiwa.domain.point.dto.PointDto;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PointService {
    private final JwtUtil jwtUtil;
    private final PointRepository pointRepository;
    private final UserRepository userRepository;

    public PointService(JwtUtil jwtUtil, PointRepository pointRepository, UserRepository userRepository) {
        this.jwtUtil = jwtUtil;
        this.pointRepository = pointRepository;
        this.userRepository = userRepository;
    }

    public Page<PointDto> getPointHistory(String token, Pageable pageable) {
        Long userId = jwtUtil.getId(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return pointRepository.findByUserOrderByCreatedAtDesc(user, pageable)
                .map(PointDto::from);
    }

    public Integer getTotalPoints(String token) {
        Long userId = jwtUtil.getId(token);

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        return pointRepository.findTotalPointsByUser(user);
    }
}



