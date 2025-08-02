package com.semiton.Lachelein.animeList.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecommendationResponse {
    private List<TmdbResult> results;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class TmdbResult {
        private int id;
        private String name;
    }
}
