package com.capstone_breaking.newtral.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ResponseNewsForm {

    private List<ResponseArticleCompany> responseArticleCompanies;

    private String author;

    private String title;

    private String description;

    private String url;

    private String urlToImage;

    private LocalDateTime publishedAt;

    private String content;

    @Builder
    public ResponseNewsForm(List<ResponseArticleCompany> responseArticleCompanies, String author,
                            String title, String description, String url, String urlToImage,
                            LocalDateTime publishedAt, String content){
        this.responseArticleCompanies = responseArticleCompanies;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }
}
