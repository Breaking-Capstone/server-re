package com.capstone_breaking.newtral.domain;

import com.capstone_breaking.newtral.dto.Article.ResponseArticle;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Column(length = 5000)
    private String description;

    private String contentShort;

    private String company;

    private String author;

    private String url;

    private String urlImage;

    private LocalDateTime publishedAt;

    private Long percent1;

    private Long percent2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "article_id")
    private Category category;


    @Builder
    public Article(String title, String description, String contentShort,
                   String company,String author,String url, String urlImage,
                   LocalDateTime publishedAt ,Category category){
        this.company = company;
        this.author = author;
        this.url = url;
        this.urlImage = urlImage;
        this.publishedAt = publishedAt;
        this.description = description;
        this.contentShort = contentShort;
        this.title = title;
        this.category = category;
    }

    public Article setPercent(Long percent1, Long percent2){
        this.percent1 = percent1;
        this.percent2 = percent2;

        return this;
    }



}
