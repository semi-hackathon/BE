package com.semiton.Lachelein.member.controller;

import com.semiton.Lachelein.member.dto.ApiResponse;
import com.semiton.Lachelein.member.dto.AuthResponseDTO.LoginResponse;
import com.semiton.Lachelein.member.dto.KakaoLogin_code;
import com.semiton.Lachelein.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/kakao/login")
    public ApiResponse<LoginResponse> kakaoLogin(@RequestBody KakaoLogin_code request) { // DTO 타입으로 받아야함
        return ApiResponse.onSuccess("카카오 로그인 성공", memberService.kakaoLogin(request.getCode()));
    }

    @PostMapping("/kakao/logout")
    public ResponseEntity<ApiResponse<Void>> kakaoLogout(@RequestHeader("Authorization") String authHeader) {
        String accessToken = authHeader.replace("Bearer ", "").trim();
        memberService.kakaoLogout(accessToken);
        return ResponseEntity.ok(ApiResponse.onSuccess("카카오 로그아웃 성공", null));
    }
}
