package com.capstone_breaking.newtral.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseArticleCompany {

    private String id;

    private String company;

    @Builder
    public ResponseArticleCompany(String id, String company){
        this.id = id;
        this.company = company;
    }

}
