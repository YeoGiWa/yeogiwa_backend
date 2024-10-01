package com.example.yeogiwa.domain.user.dto;

import com.example.yeogiwa.domain.user.UserEntity;
import com.example.yeogiwa.enums.Role;
import lombok.*;

import java.time.LocalDateTime;

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
}