package com.semiton.Lachelein.animeList.domain;

import com.semiton.Lachelein.animeList.converter.IntegerListConverter;
import com.semiton.Lachelein.member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class AnimeList {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id") // DB에 생성될 외래 키(Foreign Key) 컬럼 이름
    private Member member;

    @Convert(converter = IntegerListConverter.class)
    @Column(length = 1000) // 저장될 문자열 길이를 충분히 설정
    private List<Integer> animeIds;

    public AnimeList(Member member, List<Integer> animeIds) {
        this.member = member;
        this.animeIds = animeIds;
    }
}
