package com.capstone_breaking.newtral.dto.Article;

import com.capstone_breaking.newtral.dto.Article.NewsApi.ResponseArticleCompany;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "article 응답 Dto(article 복수응답용)")
public class ResponseArticleForm {

    @Schema(description = "유저 흥미", example = "science")
    private String interest;

    @Schema(description = "흥미에 맞는 뉴스 리스트")
    private List<ResponseArticle> articles;

    @Builder
    public ResponseArticleForm(String interest, List<ResponseArticle> articles){
        this.interest = interest;
        this.articles = articles;
    }

}
