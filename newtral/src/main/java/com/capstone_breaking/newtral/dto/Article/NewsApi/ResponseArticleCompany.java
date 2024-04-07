package com.capstone_breaking.newtral.dto.Article.NewsApi;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseArticleCompany {

    private String id;

    private String name;

    @Builder
    public ResponseArticleCompany(String id, String name){
        this.id = id;
        this.name = name;
    }

}
