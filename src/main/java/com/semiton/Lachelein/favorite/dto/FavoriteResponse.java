package com.semiton.Lachelein.favorite.dto;

import com.semiton.Lachelein.favorite.domain.Favorite;
import lombok.Getter;

@Getter
public class FavoriteResponse {
    private final Long id;
    private final Integer tmdbId;
    private final String contentType;

    public FavoriteResponse(Favorite favorite) {
        this.id = favorite.getId();
        this.tmdbId = favorite.getTmdbId();
        this.contentType = favorite.getContentType();
    }
}
