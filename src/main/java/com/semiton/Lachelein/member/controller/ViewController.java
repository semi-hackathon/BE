/*
package com.semiton.Lachelein.member.controller;

import com.semiton.Lachelein.member.dto.ApiResponse;
import com.semiton.Lachelein.member.dto.KakaoLogin_code;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Controller
@RequiredArgsConstructor
public class ViewController {

    @Value("${kakao.client}")
    private String kakaoRestApiKey;

    @Value("${kakao.redirect-uri}")
    private String redirectUri;

    // 1. 홈에서 인증 URL 제공 (GET)
    @GetMapping("/")
    public String home(Model model) {
        String kakaoAuthUrl = "https://kauth.kakao.com/oauth/authorize" +
                "?client_id=" + kakaoRestApiKey +
                "&redirect_uri=" + redirectUri +
                "&response_type=code";
        model.addAttribute("kakaoAuthUrl", kakaoAuthUrl);
        return "home";
    }

    // 2. 카카오 콜백(GET): REST API 연동
    @GetMapping("/auth/kakao/callback")
    public String kakaoCallback(@RequestParam("code") String code, Model model) {
        // KakaoLogin_code DTO 생성
        KakaoLogin_code requestDto = new KakaoLogin_code();
        requestDto.setCode(code);

        // REST API 호출 (POST)
        String apiUrl = "http://localhost:8080/api/auth/kakao/login";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<KakaoLogin_code> entity = new HttpEntity<>(requestDto, headers);

        ResponseEntity<ApiResponse> response = restTemplate.postForEntity(apiUrl, entity, ApiResponse.class);

        // 결과를 Model에 담기
        model.addAttribute("apiResponse", response.getBody());
        return "kakaoResult";
    }

    // 3. 로그아웃 View (accessToken 입력 받아서 처리)
    @GetMapping("/auth/kakao/logout")
    public String kakaoLogoutPage(@RequestParam(value = "accessToken", required = false) String accessToken, Model model) {
        model.addAttribute("accessToken", accessToken);
        return "kakaoLogout";
    }

    // 4. 로그아웃 실제 처리 (POST)
    @PostMapping("/auth/kakao/logout")
    public String kakaoLogoutSubmit(@RequestParam("accessToken") String accessToken, Model model) {
        String apiUrl = "http://localhost:8080/api/auth/kakao/logout";
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + accessToken);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<ApiResponse> response = restTemplate.postForEntity(apiUrl, entity, ApiResponse.class);
            model.addAttribute("logoutResult", "카카오 로그아웃 성공! 🎉");
        } catch (Exception e) {
            model.addAttribute("logoutResult", "카카오 로그아웃 실패: " + e.getMessage());
        }
        return "kakaoLogout";
    }
}
*/
