package com.example.yeogiwa.domain.host.dto;

import com.example.yeogiwa.domain.host.HostEntity;
import com.example.yeogiwa.domain.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostDto {
    private Long id;

    private UserDto user;

    private String name;

    private LocalDateTime createdAt;

    private Boolean isDeleted;

    public static HostDto from(HostEntity hostEntity) {
        UserDto user = UserDto.from(hostEntity.getUser());

        return HostDto.builder()
            .id(hostEntity.getId())
            .user(user)
            .createdAt(hostEntity.getCreatedAt())
            .isDeleted(hostEntity.getIsDeleted())
            .build();
    }
}