package com.capstone_breaking.newtral.controller;

import com.capstone_breaking.newtral.common.CommonResponse;
import com.capstone_breaking.newtral.service.ArticleService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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
    public ResponseEntity<CommonResponse> getNewsAtApi() throws JsonProcessingException,JsonMappingException {
        articleService.getNewsAtApi();
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", null));
    }

    @GetMapping("/articles")
    public ResponseEntity<CommonResponse> getNews(Long firstId, Long lastId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", articleService.getNews(firstId,lastId)));
    }

    @PatchMapping("/articles/{id}")
    public ResponseEntity<CommonResponse> recodeNewsAccuracy(@PathVariable Long id, Long percent1, Long percent2){
        articleService.setNewsCurrent(id,percent1,percent2);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", null));
    }

    @GetMapping("/articles/count")
    public ResponseEntity<CommonResponse> getArticleCount(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", articleService.getArticleCount()));
    }

    @GetMapping("/user/interests/articles")
    public ResponseEntity<CommonResponse> getUserInterestArticles(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", articleService.getUserInterestArticle(userDetails)));
    }

}