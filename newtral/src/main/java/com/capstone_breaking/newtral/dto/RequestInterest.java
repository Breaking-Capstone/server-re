package com.capstone_breaking.newtral.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

import java.util.List;

@Getter
public class RequestInterest {

    @Schema(description = "유저의 흥미 리스트.")
    private List<String> interests;

}
