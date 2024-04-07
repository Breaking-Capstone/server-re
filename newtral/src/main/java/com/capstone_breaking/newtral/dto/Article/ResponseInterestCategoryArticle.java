package com.capstone_breaking.newtral.dto.Article;

import com.capstone_breaking.newtral.dto.Article.ResponseArticle;
import com.capstone_breaking.newtral.dto.Article.ResponseArticleForm;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseInterestCategoryArticle {

    private Integer count;

    private List<ResponseArticleForm> responseArticleForms;

    @Builder
    public ResponseInterestCategoryArticle(Integer count, List<ResponseArticleForm> responseArticleForms){
        this.count = count;
        this.responseArticleForms = responseArticleForms;
    }
}
