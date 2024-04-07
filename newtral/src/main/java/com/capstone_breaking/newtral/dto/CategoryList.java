package com.capstone_breaking.newtral.dto;

import lombok.Getter;

@Getter
public enum CategoryList {

    business(1L, "business"),
    entertainment(2L,"entertainment"),
    general(3L,"general"),
    health(4L,"health"),
    science(5L,"science"),
    sports(6L,"sports"),
    technology(7L,"technology");

    Long id;

    String name;
    CategoryList(Long id, String name){
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

}
