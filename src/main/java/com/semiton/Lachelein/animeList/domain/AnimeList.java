package com.semiton.Lachelein.animeList.domain;

import com.semiton.Lachelein.animeList.converter.IntegerListConverter;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member_id")
    @Convert(converter = IntegerListConverter.class)
    @Column(length = 1000) // 저장될 문자열 길이를 충분히 설정
    private List<Integer> animeIds;

    public AnimeList(List<Integer> animeIds) {
        this.animeIds = animeIds;
    }
}
