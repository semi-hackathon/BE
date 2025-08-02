package com.semiton.Lachelein.animeList.service;

import com.semiton.Lachelein.animeList.domain.AnimeList;
import com.semiton.Lachelein.animeList.dto.AnimeListResponse;
import com.semiton.Lachelein.animeList.dto.RecommendationRequest;
import com.semiton.Lachelein.animeList.repository.AnimeListRepository;
import com.semiton.Lachelein.member.entity.Member;
import com.semiton.Lachelein.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class RecommendService {
    private final ChatClient chatClient;
    private final TmdbService tmdbService ;
    private final AnimeListRepository animeListRepository;
    private final MemberRepository memberRepository; // MemberRepository 주입


    public RecommendService(ChatClient.Builder chatClientBuilder, TmdbService tmdbService, AnimeListRepository animeListRepository, MemberRepository memberRepository) {
        this.chatClient =chatClientBuilder.build();
        this.tmdbService = tmdbService;
        this.animeListRepository = animeListRepository;
        this.memberRepository = memberRepository;
    }

    @Transactional
    public List<Integer> getRecommendedAnimes(Long memberId ,RecommendationRequest recommendationRequest) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. ID: " + memberId));

        // 1. 상세한 영문 프롬프트 생성
        String promptString = """
                아래 사용자의 취향을 바탕으로 애니메이션 5개를 추천해 줘.
                추천하는 애니메이션의 제목만 정확하게 쉼표(,)로 구분해서 알려줘.
                다른 설명, 숫자, 부연 설명은 절대 추가하지 마.

                사용자 취향:
                - Q1. 원하는 분위기: {q1}
                - Q2. 선호하는 장르: {q2}
                - Q3. 이야기 중심: {q3}
                - Q4. 전개 속도: {q4}
                - Q5. 선호하는 주제/메시지: {q5}

                출력 예시: 카우보이 비밥, 스파이 패밀리, 사이버펑크: 엣지러너, 원펀맨, 나만이 없는 거리
                """;

        PromptTemplate promptTemplate = new PromptTemplate(promptString);
        Prompt prompt = promptTemplate.create(Map.of(
                "q1", recommendationRequest.getQ1(),
                "q2", recommendationRequest.getQ2(),
                "q3", recommendationRequest.getQ3(),
                "q4", recommendationRequest.getQ4(),
                "q5", recommendationRequest.getQ5()
        ));
        String rawResponse = chatClient.prompt(prompt).call().content();
        // 3. 응답 파싱 및 TMDB ID 조회
        List<String> animeTitles = Arrays.stream(rawResponse.split(","))
                .map(String::trim)
                .filter(title -> !title.isEmpty())
                .toList();
        List<Integer> recommendedAnimeIds =  animeTitles.stream()
                .map(tmdbService::getAnimeId) // 각 제목으로 TMDB ID 조회
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
        if (!recommendedAnimeIds.isEmpty()) {
            AnimeList animeList = new AnimeList(member, recommendedAnimeIds);
            animeListRepository.save(animeList);
        }
        return recommendedAnimeIds;
    }
    public List<AnimeListResponse> getAllAnimeLists() {
        return animeListRepository.findAll().stream()
                .map(AnimeListResponse::new) // .map(animeList -> new AnimeListResponse(animeList)) 와 동일
                .collect(Collectors.toList());
    }
}
