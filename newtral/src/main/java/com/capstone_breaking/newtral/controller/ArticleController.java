package com.capstone_breaking.newtral.controller;

import com.capstone_breaking.newtral.common.CommonResponse;
import com.capstone_breaking.newtral.dto.Article.ResponseArticle;
import com.capstone_breaking.newtral.dto.Article.ResponseArticleForAI;
import com.capstone_breaking.newtral.dto.Article.ResponseArticleForAIForm;
import com.capstone_breaking.newtral.dto.Article.ResponseInterestCategoryArticle;
import com.capstone_breaking.newtral.service.ArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("")
public class ArticleController {

    private final ArticleService articleService;
    @GetMapping("/articles/newsapi")
    @Operation(summary = "newsApi에서 뉴스 받아오기(건들지 마라.)", description = "newsApi에서 주제별로 인기있는 뉴스(newsApi에서 판단)를 받아와서 데이터베이스에 저장한다.")
    public ResponseEntity<CommonResponse> getNewsAtApi() throws JsonProcessingException,JsonMappingException {
        articleService.getNewsAtApi();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", null));
    }

    @GetMapping("/articles")
    @Operation(summary = "데이터베이스에서 뉴스 받아오기", description = "데이터베이스에서 뉴스를 받아온다.<br><br><br> 입력: <br><br>데이터베이스에서 꺼내오려는 뉴스의 PK 범위<br><br>Long firstId: 데이터베이스에서 불러오려는 뉴스의 PK(시작번호)<br><br>Long lastId: 데이터베이스에서 불러오려는 뉴스의 PK(끝번호) <br><br><br> 출력: <br> ResponseArticleForAIForm(DTO)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데이터베이스에서 뉴스 받아오기 성공", content = @Content(schema = @Schema(implementation = ResponseArticleForAIForm.class))),
    })
    public ResponseEntity<CommonResponse> getNews(Long firstId, Long lastId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", articleService.getNews(firstId,lastId)));
    }

    @PatchMapping("/articles/percent/{id}")
    @Operation(summary = "뉴스의 신뢰도 측정 결과 저장하기", description = "신뢰도 측정을 한 값을 데이터베이스에 저장한다.<br><br><br> 입력: <br><br> Long id: 뉴스의 데이터베이스상 pk값 <br><br> Long percent1: 신뢰도값1 <br><br> Long percent2: 신뢰도값2 (신뢰도 측정해야되는게 두개라고 들어서 일단 두개 넣어놓음. 하나만 넣어도 무관. 만약 하나만 넣는거면 percent2에 null이나 0을 넣을 것.)<br><br><br> 출력:<br> null")
    public ResponseEntity<CommonResponse> recodeNewsAccuracy(@PathVariable Long id, Long percent1, Long percent2){
        articleService.setNewsCurrent(id,percent1,percent2);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", null));
    }

    @GetMapping("/articles/count")
    @Operation(summary = "데이터베이스에 뉴스가 몇개 저장되어있는가 보기", description = "입력: <br> null<br><br> 출력:<br> Long값의 갯수")
    public ResponseEntity<CommonResponse> getArticleCount(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", articleService.getArticleCount()));
    }

    @GetMapping("/users/articles/interests")
    @Operation(summary = "유저의 흥미에 맞는 뉴스 불러오기", description = "유저의 흥미 당 뉴스 10개씩 불러옴.(보완이 필요하다.) <br><br> 입력: <br> 필요음슴(토큰이나 넣어라 인간!) <br><br> 출력: <br> ResponseInterestCategoryArticle(DTO)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데이터베이스에서 뉴스 받아오기 성공", content = @Content(schema = @Schema(implementation = ResponseInterestCategoryArticle.class))),
    })
    public ResponseEntity<CommonResponse> getUserInterestArticles(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", articleService.getUserInterestArticle(userDetails)));
    }

    @GetMapping ("/articles/search/{keyword}")
    @Operation(summary = "전체 뉴스에서 검색하기", description = "제목+내용에서 검색한다. 제목이 먼저나오고 내용이 뒤에나온다 <br><br> 입력: <br> 검색할 단어 <br><br> 출력: <br> List<ResponseArticle>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데이터베이스에서 뉴스 받아오기 성공", content = @Content(schema = @Schema(implementation = ResponseArticle.class))),
    })
    public ResponseEntity<CommonResponse> getSearchArticles(@PathVariable String keyword){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", articleService.getSearchNews(keyword)));
    }

    @GetMapping("/articles/{reliability_p}")
    @Operation(summary = "신뢰도 위의 뉴스만 출력", description = "신뢰도 위의 뉴스만 출력(전체뉴스 중) <br><br> 입력: <br> 신뢰도(기본값: 60) <br><br> 출력: <br> List<ResponseArticle>")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데이터베이스에서 뉴스 받아오기 성공", content = @Content(schema = @Schema(implementation = ResponseInterestCategoryArticle.class))),
    })
    public ResponseEntity<CommonResponse> getArticles(
            @PathVariable @RequestParam(defaultValue = "60", required = false) Long reliability_p){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", articleService.getArticles(reliability_p)));
    }

    @GetMapping("/articles/content/{article_id}")
    @Operation(summary = "뉴스 내용 불러오기", description = "뉴스 내용 불러옴 <br><br> 입력: <br> 뉴스 pk <br><br> 출력: <br> ResponseArticle")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데이터베이스에서 뉴스 받아오기 성공", content = @Content(schema = @Schema(implementation = ResponseArticle.class))),
    })
    public ResponseEntity<CommonResponse> getArticle(@PathVariable Long article_id){
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("OK", articleService.getArticle(article_id)));
    }
}
