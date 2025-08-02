package com.semiton.Lachelein.animeList.controller;

import com.semiton.Lachelein.animeList.dto.AnimeListResponse;
import com.semiton.Lachelein.animeList.dto.RecommendationRequest;
import com.semiton.Lachelein.animeList.service.RecommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/anime")
@RequiredArgsConstructor
public class AnimeController {
    private final RecommendService recommendService;

    @PostMapping("/recommend/{memberId}")
    public ResponseEntity<List<Integer>> recommend(
            @PathVariable Long memberId // @PathVariable로 memberId 받기
            ,@RequestBody RecommendationRequest request) {
        List<Integer> recommendedAnimeIds = recommendService.getRecommendedAnimes(memberId, request);
        return ResponseEntity.ok(recommendedAnimeIds);
    }
    /**
     * 모든 추천 리스트를 조회하는 API
     * 요청 예시: GET /api/anime/lists
     */
    @GetMapping("/lists")
    public ResponseEntity<List<AnimeListResponse>> getAllAnimeLists() {
        List<AnimeListResponse> response = recommendService.getAllAnimeLists();
        return ResponseEntity.ok(response);
    }
}
