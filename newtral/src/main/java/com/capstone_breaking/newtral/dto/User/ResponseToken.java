package com.capstone_breaking.newtral.dto.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ResponseToken {

    private String refreshToken;

    private String accessToken;

    @Builder
    private ResponseToken(String refreshToken, String accessToken) {
        this.refreshToken = refreshToken;
        this.accessToken = accessToken;
    }

}
