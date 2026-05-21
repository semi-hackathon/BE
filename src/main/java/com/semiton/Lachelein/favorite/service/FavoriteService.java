package com.semiton.Lachelein.favorite.service;

import com.semiton.Lachelein.favorite.domain.Favorite;
import com.semiton.Lachelein.favorite.dto.FavoriteRequest;
import com.semiton.Lachelein.favorite.dto.FavoriteResponse;
import com.semiton.Lachelein.favorite.repository.FavoriteRepository;
import com.semiton.Lachelein.member.entity.Member;
import com.semiton.Lachelein.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FavoriteService {

    private final FavoriteRepository favoriteRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public FavoriteResponse addFavorite(Long memberId, FavoriteRequest request) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. ID: " + memberId));

        if (favoriteRepository.existsByMemberIdAndTmdbId(memberId, request.getTmdbId())) {
            throw new IllegalStateException("이미 찜한 콘텐츠입니다.");
        }

        Favorite favorite = new Favorite(member, request.getTmdbId(), request.getContentType());
        return new FavoriteResponse(favoriteRepository.save(favorite));
    }

    @Transactional
    public void removeFavorite(Long memberId, Integer tmdbId) {
        Favorite favorite = favoriteRepository.findByMemberIdAndTmdbId(memberId, tmdbId)
                .orElseThrow(() -> new IllegalArgumentException("찜 목록에 없는 콘텐츠입니다."));
        favoriteRepository.delete(favorite);
    }

    @Transactional(readOnly = true)
    public List<FavoriteResponse> getFavorites(Long memberId) {
        return favoriteRepository.findByMemberId(memberId).stream()
                .map(FavoriteResponse::new)
                .collect(Collectors.toList());
    }
}
