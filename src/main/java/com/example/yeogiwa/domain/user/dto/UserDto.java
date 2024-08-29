package com.example.yeogiwa.domain.user.dto;

import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private UUID id;

    private String email;

    private String password;

    private String name;

    private Role role;

    private LocalDateTime createdAt;

    public static UserDto from(UserEntity userEntity) {
        return UserDto.builder()
            .id(userEntity.getId())
            .email(userEntity.getEmail())
            .password(userEntity.getPassword())
            .name(userEntity.getName())
            .role(userEntity.getRole())
            .createdAt(userEntity.getCreatedAt())
            .build();
    }
}