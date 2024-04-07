package com.capstone_breaking.newtral.dto.Article;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseArticleForAI {

    private Long id;

    private String title;

    private String content;

    @Builder
    public ResponseArticleForAI(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
