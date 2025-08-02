package com.semiton.Lachelein.animeList.dto;

import com.semiton.Lachelein.animeList.domain.AnimeList;
import lombok.Getter;

import java.util.List;

@Getter
public class AnimeListResponse {
    private final Long id;
    private final List<Integer> animeId;

    public AnimeListResponse(AnimeList animeList) {
        this.id = animeList.getId();
        this.animeId = animeList.getAnimeIds();
    }
}
