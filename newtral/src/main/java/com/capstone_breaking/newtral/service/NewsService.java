package com.capstone_breaking.newtral.service;

import com.capstone_breaking.newtral.domain.News;
import com.capstone_breaking.newtral.dto.ResponseArticleCompany;
import com.capstone_breaking.newtral.dto.ResponseNews;
import com.capstone_breaking.newtral.dto.ResponseNewsApi;
import com.capstone_breaking.newtral.dto.ResponseNewsForm;
import com.capstone_breaking.newtral.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewsService {

    private final NewsRepository newsRepository;


    public void getNewsAtApi(){
        WebClient webClient = WebClient.create("https://newsapi.org/v2");

        webClient.get()
                .uri(uriBuilder -> uriBuilder.path("/top-headlines")
                        .queryParam("country", "kr")
                        .queryParam("apiKey", "4c9503509a624941b925e2500cd8a531")
                        .build())
                .retrieve()
                .bodyToMono(ResponseNewsApi.class)
                .subscribe(responseNewsApi -> {
                    List<News> apiResult = responseNewsApi.getArticles().stream().map(
                            responseNewsForm -> new News(responseNewsForm.getTitle(), responseNewsForm.getDescription(), null, null)).toList();
                    newsRepository.saveAll(apiResult);
                });
    }

    public List<ResponseNews> getNews(Long firstId, Long endId){
        List<News> newsList = newsRepository.findByIdBetween(firstId, endId);

        List<ResponseNews> responseNews = newsList.stream().map(news -> new ResponseNews(news.getId(), news.getTitle(), news.getContent())).toList();

        return responseNews;
    }

    public void setNewsCurrent(Long id, Long percent1, Long percent2){
        Optional<News> news = newsRepository.findById(id);

        newsRepository.save(news.get().setPercent(percent1, percent2));
    }

    public Long getArticleCount(){
        return newsRepository.count();
    }

}
