package com.capstone_breaking.newtral.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String field;

    @Column(length = 5000)
    private String content;

    private String contentShort;

    private String title;

    private Long percent1;

    private Long percent2;

    @Builder
    public News(String title, String content, String contentShort, String field){
        this.content = content;
        this.contentShort = contentShort;
        this.title = title;
        this.field = field;
    }

    public News setPercent(Long percent1, Long percent2){
        this.percent1 = percent1;
        this.percent2 = percent2;

        return this;
    }

}
