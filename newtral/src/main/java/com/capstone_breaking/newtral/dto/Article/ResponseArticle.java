package com.capstone_breaking.newtral.dto.Article;

import com.capstone_breaking.newtral.domain.Article;
import com.capstone_breaking.newtral.domain.Category;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Schema(description = "article 응답 dto")
public class ResponseArticle {

    @Schema(description = "뉴스의 데이터베이스 상 PK", example = "1")
    private Long id;

    @Schema(description = "뉴스 제목", example = "(충격)인천대학교 19학번 서호준, 바보였다는 사실 발견...정지훈 오열")
    private String title;

    @Schema(description = "뉴스 본문", example = "인천대학교 19학번 서호준이 바보였다는 사실이 오늘(19)일 밝혀졌다. 인천대학교 측은 '전혀 몰랐다', '그럴리가 없다' 라는 의견을 내세웠지만...")
    private String description;

    @Schema(description = "뉴스 본문 요약본", example = "인천대학교 19학번 서호준이 바보였다는 사실이 오늘(19)일 밝혀졌다.")
    private String contentShort;

    @Schema(description = "뉴스사", example = "횃불뉴스")
    private String company;

    @Schema(description = "기자", example = "횃불이")
    private String author;

    @Schema(description = "뉴스 링크", example = "https://대충_링크라는_표정.com")
    private String url;

    @Schema(description = "뉴스 사진 링크", example = "https://대충_사진_링크라는_표정.com")
    private String urlImage;

    @Schema(description = "뉴스 작성시간", example = "2024-01-31T21:30:00+09:00")
    private LocalDateTime publishedAt;

    @Schema(description = "뉴스 신뢰도", example = "형식 아직 받은게없어서 몰루!")
    private Float percent1;

    @Schema(description = "뉴스 신뢰도", example = "형식 아직 받은게없어서 몰루!")
    private Float percent2;
    @Builder
    public ResponseArticle(Long id, String title, String description, String contentShort,
                   String company, String author, String url, String urlImage,
                   LocalDateTime publishedAt, Float percent1, Float percent2){
        this.id = id;
        this.company = company;
        this.author = author;
        this.url = url;
        this.urlImage = urlImage;
        this.publishedAt = publishedAt;
        this.description = description;
        this.contentShort = contentShort;
        this.title = title;
        this.percent1=percent1;
        this.percent2=percent2;
    }

    public ResponseArticle toDto(Article article){
        this.id = article.getId();
        this.company = article.getCompany();
        this.author = article.getAuthor();
        this.url = article.getUrl();
        this.urlImage = article.getUrlImage();
        this.publishedAt = article.getPublishedAt();
        this.description = article.getDescription();
        this.contentShort = article.getContentShort();
        this.title = article.getTitle();
        this.percent1=article.getPercent1();
        this.percent2=article.getPercent2();

        return this;
    }

}
