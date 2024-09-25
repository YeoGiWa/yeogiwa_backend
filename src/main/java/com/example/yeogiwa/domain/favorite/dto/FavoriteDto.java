package com.example.yeogiwa.domain.favorite.dto;

import com.example.yeogiwa.domain.favorite.FavoriteEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteDto {
    private Long id;
    private Long userId;
    private Long eventId;

    public static FavoriteDto from(FavoriteEntity favoriteEntity) {
        return FavoriteDto.builder()
                .id(favoriteEntity.getId())
                .userId(favoriteEntity.getUser().getId())
                .eventId(favoriteEntity.getEvent().getId())
                .build();
    }
}
