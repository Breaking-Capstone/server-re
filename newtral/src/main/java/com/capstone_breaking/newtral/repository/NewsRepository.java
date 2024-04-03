package com.capstone_breaking.newtral.repository;

import com.capstone_breaking.newtral.domain.News;
import com.capstone_breaking.newtral.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NewsRepository  extends JpaRepository<News, Long> {

    List<News> findByIdBetween(Long startId, Long endId);

}
