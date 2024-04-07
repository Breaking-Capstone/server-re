package com.capstone_breaking.newtral.repository;

import com.capstone_breaking.newtral.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findByIdBetween(Long startId, Long endId);

    List<Article> findTop10ByCategoryId(Long categoryId);

}
