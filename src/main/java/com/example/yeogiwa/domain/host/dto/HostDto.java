package com.example.yeogiwa.domain.host.dto;

import com.example.yeogiwa.domain.event.SessionEntity;
import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.domain.user.dto.UserDto;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostDto {
    private UUID id;

    private UserDto user;

    private String name;

    private LocalDateTime createdAt;

    private Boolean isDeleted;

    public static HostDto from(HostEntity hostEntity) {
        UserDto user = UserDto.from(hostEntity.getUser());

        return HostDto.builder()
            .id(hostEntity.getId())
            .user(user)
            .name(hostEntity.getName())
            .createdAt(hostEntity.getCreatedAt())
            .isDeleted(hostEntity.getIsDeleted())
            .build();
    }
}