package com.semiton.Lachelein.favorite.controller;

import com.semiton.Lachelein.favorite.dto.FavoriteRequest;
import com.semiton.Lachelein.favorite.dto.FavoriteResponse;
import com.semiton.Lachelein.favorite.service.FavoriteService;
import com.semiton.Lachelein.member.dto.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/favorites")
@Tag(name = "Favorite", description = "찜하기 API")
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "찜하기 추가", description = "회원이 콘텐츠를 찜 목록에 추가합니다.")
    @PostMapping("/{memberId}")
    public ResponseEntity<ApiResponse<FavoriteResponse>> addFavorite(
            @PathVariable Long memberId,
            @RequestBody FavoriteRequest request
    ) {
        FavoriteResponse response = favoriteService.addFavorite(memberId, request);
        return ResponseEntity.ok(ApiResponse.onSuccess("찜하기 추가 성공", response));
    }

    @Operation(summary = "찜하기 취소", description = "회원의 찜 목록에서 콘텐츠를 제거합니다.")
    @DeleteMapping("/{memberId}/{tmdbId}")
    public ResponseEntity<ApiResponse<Void>> removeFavorite(
            @PathVariable Long memberId,
            @PathVariable Integer tmdbId
    ) {
        favoriteService.removeFavorite(memberId, tmdbId);
        return ResponseEntity.ok(ApiResponse.onSuccess("찜하기 취소 성공", null));
    }

    @Operation(summary = "찜 목록 조회", description = "회원의 찜 목록을 조회합니다.")
    @GetMapping("/{memberId}")
    public ResponseEntity<ApiResponse<List<FavoriteResponse>>> getFavorites(
            @PathVariable Long memberId
    ) {
        List<FavoriteResponse> response = favoriteService.getFavorites(memberId);
        return ResponseEntity.ok(ApiResponse.onSuccess("찜 목록 조회 성공", response));
    }
}
