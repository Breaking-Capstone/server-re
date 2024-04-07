package com.capstone_breaking.newtral.dto.Article;

import com.capstone_breaking.newtral.domain.Article;
import com.capstone_breaking.newtral.domain.Category;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseArticle {

    private Long id;

    private String title;

    private String description;

    private String contentShort;

    private String company;

    private String author;

    private String url;

    private String urlImage;

    private LocalDateTime publishedAt;

    private Long percent1;

    private Long percent2;
    @Builder
    public ResponseArticle(Long id, String title, String description, String contentShort,
                   String company, String author, String url, String urlImage,
                   LocalDateTime publishedAt, Long percent1, Long percent2){
        this.id = id;
        this.company = company;
        this.author = author;
        this.url = url;
        this.urlImage = urlImage;
        this.publishedAt = publishedAt;
        this.description = description;
        this.contentShort = contentShort;
        this.title = title;
        this.percent1=percent1;
        this.percent2=percent2;
    }

    public ResponseArticle toDto(Article article){
        this.id = article.getId();
        this.company = article.getCompany();
        this.author = article.getAuthor();
        this.url = article.getUrl();
        this.urlImage = article.getUrlImage();
        this.publishedAt = article.getPublishedAt();
        this.description = article.getDescription();
        this.contentShort = article.getContentShort();
        this.title = article.getTitle();
        this.percent1=article.getPercent1();
        this.percent2=article.getPercent2();

        return this;
    }

}
