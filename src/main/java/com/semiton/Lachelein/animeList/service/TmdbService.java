package com.semiton.Lachelein.animeList.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semiton.Lachelein.animeList.dto.RecommendationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;

@Service
public class TmdbService {

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String tmdbBearerToken;
    private final String tmdbApiUrl;

    public TmdbService(HttpClient httpClient,
                       ObjectMapper objectMapper,
                       @Value("${tmdb.api.key}") String tmdbBearerToken,
                       @Value("${tmdb.api.url}") String tmdbApiUrl) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
        this.tmdbBearerToken = tmdbBearerToken;
        this.tmdbApiUrl = tmdbApiUrl;
    }

    public Optional<Integer> getAnimeId(String animeTitle) {
        // 1. 요청 URI 생성
        String url = UriComponentsBuilder.fromHttpUrl(tmdbApiUrl + "/search/tv")
                .queryParam("query", animeTitle)
                .queryParam("language", "ko-KR")
                .queryParam("include_adult", "false")
                .toUriString();

        // 2. HttpRequest 객체 생성
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("accept", "application/json")
                .header("Authorization", "Bearer " + tmdbBearerToken)
                .GET() // .method("GET", HttpRequest.BodyPublishers.noBody()) 와 동일
                .build();

        try {
            // 3. API 요청 및 응답 수신
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // 4. JSON 응답 문자열을 DTO 객체로 변환
            RecommendationResponse searchResponse = objectMapper.readValue(response.body(), RecommendationResponse.class);

            if (searchResponse != null && !searchResponse.getResults().isEmpty()) {
                return Optional.of(searchResponse.getResults().get(0).getId());
            }

        } catch (Exception e) {
            // 실제 프로덕션에서는 더 정교한 로깅이 필요합니다.
            System.err.println("Error fetching TMDB ID for title: " + animeTitle + " - " + e.getMessage());
        }

        return Optional.empty();
        }
    }
