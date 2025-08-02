package com.semiton.Lachelein.animeList.dto;

import lombok.Data;

@Data
public class RecommendationRequest {
    private String q1; // Q1. 어떤 분위기의 애니를 좋아하나요?
    private String q2; // Q2. 선호하는 장르는 무엇인가요?
    private String q3; // Q3. 캐릭터 중심 vs. 세계관 중심
    private String q4; // Q4. 전개 속도
    private String q5; // Q5. 메시지나 주제
}
