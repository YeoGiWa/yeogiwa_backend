package com.example.yeogiwa.domain.ambassador.dto;

import com.example.yeogiwa.domain.ambassador.AmbassadorEntity;
import com.example.yeogiwa.domain.user.dto.UserDto;
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
public class AmbassadorDto {
    private Long id;

    private byte[] qr;

    private LocalDateTime createdAt;

    private UserDto user;

    public static AmbassadorDto from(AmbassadorEntity ambassador) {
        UserDto userDto = UserDto.from(ambassador.getUser());

        return AmbassadorDto.builder()
            .id(ambassador.getId())
            .qr(ambassador.getQr())
            .createdAt(ambassador.getCreatedAt())
            .user(userDto)
            .build();
    }
}