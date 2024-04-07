package com.capstone_breaking.newtral.dto.Article.NewsApi;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseNewsApi {

    private String status;

    private Long totalResults;

    private List<ResponseNewsForm> articles;

    @Builder
    public ResponseNewsApi(String status, Long totalResults, List<ResponseNewsForm> articles){
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

}
