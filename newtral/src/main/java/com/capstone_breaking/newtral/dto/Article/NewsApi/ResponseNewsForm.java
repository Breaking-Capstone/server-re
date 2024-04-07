package com.capstone_breaking.newtral.dto.Article.NewsApi;

import com.capstone_breaking.newtral.dto.Article.NewsApi.ResponseArticleCompany;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ResponseNewsForm {

    private ResponseArticleCompany source;

    private String author;

    private String title;

    private String description;

    private String url;

    private String urlToImage;

    private LocalDateTime publishedAt;

    private String content;

    @Builder
    public ResponseNewsForm(ResponseArticleCompany source, String author,
                            String title, String description, String url, String urlToImage,
                            LocalDateTime publishedAt, String content){
        this.source = source;
        this.author = author;
        this.title = title;
        this.description = description;
        this.url = url;
        this.urlToImage = urlToImage;
        this.publishedAt = publishedAt;
        this.content = content;
    }
}
