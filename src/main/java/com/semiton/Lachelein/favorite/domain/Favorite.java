package com.semiton.Lachelein.favorite.domain;

import com.semiton.Lachelein.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Integer tmdbId;

    @Column(nullable = false)
    private String contentType; // "ANIME" | "MOVIE"

    public Favorite(Member member, Integer tmdbId, String contentType) {
        this.member = member;
        this.tmdbId = tmdbId;
        this.contentType = contentType;
    }
}
