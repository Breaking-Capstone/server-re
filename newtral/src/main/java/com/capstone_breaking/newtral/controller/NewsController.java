package com.capstone_breaking.newtral.controller;

import com.capstone_breaking.newtral.common.CommonResponse;
import com.capstone_breaking.newtral.service.NewsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("")
public class NewsController {

    private final NewsService newsService;
    @GetMapping("/article/newsapi")
    public ResponseEntity<CommonResponse> getNewsAtApi() throws JsonProcessingException,JsonMappingException {
        newsService.getNewsAtApi();
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("OK", null));
    }

    @GetMapping("/article")
    public ResponseEntity<CommonResponse> getNews(Long firstId, Long lastId){
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("OK", newsService.getNews(firstId,lastId)));
    }

    @PatchMapping("/article/{id}")
    public ResponseEntity<CommonResponse> recodeNewsAccuracy(@PathVariable Long id, Long percent1, Long percent2){
        newsService.setNewsCurrent(id,percent1,percent2);
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("OK", null));
    }

    @GetMapping("/article/count")
    public ResponseEntity<CommonResponse> getArticleCount(){
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("OK", newsService.getArticleCount()));
    }
}
