package com.capstone_breaking.newtral.dto.Article;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "article 응답 dto(ai용)")
public class ResponseArticleForAI {

    @Schema(description = "뉴스의 데이터베이스 상 PK", example = "1")
    private Long id;

    @Schema(description = "뉴스 제목", example = "(충격)인천대학교 19학번 서호준, 바보였다는 사실 발견...정지훈 오열")
    private String title;

    @Schema(description = "뉴스 본문", example = "인천대학교 19학번 서호준이 바보였다는 사실이 오늘(19)일 밝혀졌다. 인천대학교 측은 '전혀 몰랐다', '그럴리가 없다' 라는 의견을 내세웠지만...")
    private String content;

    @Builder
    public ResponseArticleForAI(Long id, String title, String content){
        this.id = id;
        this.title = title;
        this.content = content;
    }

}
