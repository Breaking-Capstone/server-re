package com.capstone_breaking.newtral.service;

import com.capstone_breaking.newtral.common.CustomUserDetails;
import com.capstone_breaking.newtral.domain.Article;
import com.capstone_breaking.newtral.domain.UserCategory;
import com.capstone_breaking.newtral.dto.Article.*;
import com.capstone_breaking.newtral.dto.Article.NewsApi.ResponseNewsApi;
import com.capstone_breaking.newtral.dto.CategoryList;
import com.capstone_breaking.newtral.repository.ArticleRepository;
import com.capstone_breaking.newtral.repository.CategoryRepository;
import com.capstone_breaking.newtral.repository.UserCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ArticleService {

    private final ArticleRepository articleRepository;

    private final CategoryRepository categoryRepository;

    private final UserCategoryRepository userCategoryRepository;

    public void getNewsAtApi() {
        WebClient webClient = WebClient.create("https://newsapi.org/v2");

        for (CategoryList category : CategoryList.values()) {
            String categoryName = category.getName();
            log.info("카테고리 이름: {}", categoryName);
            webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("/top-headlines")
                            .queryParam("country", "kr")
                            .queryParam("apiKey", "4c9503509a624941b925e2500cd8a531")
                            .queryParam("category", categoryName)
                            .build())
                    .retrieve()
                    .bodyToMono(ResponseNewsApi.class)
                    .subscribe(responseNewsApi -> {
                        List<Article> apiResult = responseNewsApi.getArticles().stream().map(
                                        responseNewsForm -> new Article(responseNewsForm.getTitle(), responseNewsForm.getDescription(), null,
                                                responseNewsForm.getSource().getName(), responseNewsForm.getAuthor(), responseNewsForm.getUrl(),
                                                responseNewsForm.getUrlToImage(), responseNewsForm.getPublishedAt(),
                                                categoryRepository.findById(CategoryList.valueOf(categoryName).getId()).get()))
                                .toList();
                        articleRepository.saveAll(apiResult);
                    });
        }

    }

    public ResponseArticleForAIForm getNews(Long firstId, Long endId) {
        List<Article> newsList = articleRepository.findByIdBetween(firstId, endId);

        ResponseArticleForAIForm responseNews =
                ResponseArticleForAIForm.builder()
                        .responseArticleForAIS(newsList.stream()
                                .map(news -> new ResponseArticleForAI(news.getId(), news.getTitle(), news.getDescription())).toList())
                        .count(newsList.size())
                        .build();


        return responseNews;
    }

    public void setNewsCurrent(Long id, Long percent1, Long percent2) {
        Optional<Article> news = articleRepository.findById(id);

        articleRepository.save(news.get().setPercent(percent1, percent2));
    }

    public Long getArticleCount() {
        return articleRepository.count();
    }


    @Transactional(readOnly = true)
    public ResponseInterestCategoryArticle getUserInterestArticle(UserDetails userDetails) {
        Long userId = ((CustomUserDetails) userDetails).getId();
        List<UserCategory> userInterest = userCategoryRepository.findUserCategoryByUserId(userId);

        List<ResponseArticleForm> responseArticleForms = new ArrayList<>();

        for (UserCategory interest : userInterest) {
            Long categoryId = interest.getCategory().getId();
            List<ResponseArticle> responseArticles = articleRepository.findTop10ByCategoryId(categoryId).stream()
                    .map(article -> new ResponseArticle(article.getId(), article.getTitle(),
                            article.getDescription(), article.getContentShort(),
                            article.getCompany(), article.getAuthor(), article.getUrl(),
                            article.getUrlImage(), article.getPublishedAt(),
                            article.getPercent1(), article.getPercent2())).toList();

            ResponseArticleForm responseArticleForm = new ResponseArticleForm(interest.getCategory().getCategoryName(), responseArticles);

            responseArticleForms.add(responseArticleForm);

        }

        ResponseInterestCategoryArticle responseInterestCategoryArticle = new ResponseInterestCategoryArticle(userInterest.size(), responseArticleForms);

        return responseInterestCategoryArticle;
    }



}
