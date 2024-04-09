package com.capstone_breaking.newtral.dto.Article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ResponseArticleForAIForm {

    @Schema(description = "불러온 뉴스 갯수",example = "1")
    private Integer count;

    private List<ResponseArticleForAI> responseArticleForAIS;

    @Builder
    public ResponseArticleForAIForm(Integer count, List<ResponseArticleForAI> responseArticleForAIS){
        this.count = count;
        this.responseArticleForAIS = responseArticleForAIS;
    }

}
