package com.capstone_breaking.newtral.dto.Article;

import com.capstone_breaking.newtral.dto.Article.NewsApi.ResponseArticleCompany;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseArticleForm {

    private String interest;

    private List<ResponseArticle> articles;

    @Builder
    public ResponseArticleForm(String interest, List<ResponseArticle> articles){
        this.interest = interest;
        this.articles = articles;
    }

}
