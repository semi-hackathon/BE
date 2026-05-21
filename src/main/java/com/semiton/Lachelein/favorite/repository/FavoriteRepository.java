package com.semiton.Lachelein.favorite.repository;

import com.semiton.Lachelein.favorite.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByMemberId(Long memberId);
    Optional<Favorite> findByMemberIdAndTmdbId(Long memberId, Integer tmdbId);
    boolean existsByMemberIdAndTmdbId(Long memberId, Integer tmdbId);
}
