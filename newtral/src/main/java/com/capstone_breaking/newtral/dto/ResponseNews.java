package com.capstone_breaking.newtral.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseNews {

    private Long id;

    private String title;

    private String content;

    @Builder
    public ResponseNews(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
