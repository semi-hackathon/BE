package com.semiton.Lachelein.animeList.repository;

import com.semiton.Lachelein.animeList.domain.AnimeList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnimeListRepository  extends JpaRepository<AnimeList, Long> {
}
