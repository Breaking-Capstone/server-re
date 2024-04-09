package com.capstone_breaking.newtral.dto.Article;

import com.capstone_breaking.newtral.dto.Article.ResponseArticle;
import com.capstone_breaking.newtral.dto.Article.ResponseArticleForm;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Schema(description = "흥미에 맞는 뉴스 불러오기용 Dto")
public class ResponseInterestCategoryArticle {

    @Schema(description = "유저의 흥미 갯수")
    private Integer count;

    @Schema(description = "유저가 가지고 있는 흥미에 해당하는 뉴스들의 리스트")
    private List<ResponseArticleForm> responseArticleForms;

    @Builder
    public ResponseInterestCategoryArticle(Integer count, List<ResponseArticleForm> responseArticleForms){
        this.count = count;
        this.responseArticleForms = responseArticleForms;
    }
}
