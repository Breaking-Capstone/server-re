package com.capstone_breaking.newtral.repository;

import com.capstone_breaking.newtral.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByIdBetween(Long startId, Long endId);

    List<Article> findTop10ByCategoryId(Long categoryId);

    List<Article> findByDescriptionContains(String keyword);

    List<Article> findByTitleContains(String keyword);

    List<Article> findByPercent1GreaterThan(Long percent);

} //정아는 바보가 맞다.
