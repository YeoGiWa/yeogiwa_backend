package com.example.yeogiwa.domain.user.dto;

import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.enums.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserDto {
    private Long id;

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

    public static UserDto from(Optional<UserEntity> userEntity) {
        return UserDto.builder()
            .id(userEntity.get().getId())
            .email(userEntity.get().getEmail())
            .password(userEntity.get().getPassword())
            .name(userEntity.get().getName())
            .role(userEntity.get().getRole())
            .createdAt(userEntity.get().getCreatedAt())
            .build();
    }
}