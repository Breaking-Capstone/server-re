package com.capstone_breaking.newtral.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "회원가입용 DTO")
public class RequestUser {

    @Schema(description = "유저의 이메일", example = "sseeddff@kakao.com")
    private String email;

    @Schema(description = "유저 이름 혹은 닉네임", example = "깨구리")
    private String name;

    @Schema(description = "유저의 프로필이미지", example = "프로필 이미지 링크.")
    private String profileImage;

    @Schema(description = "OAauth2 제공사", example = "kakao")
    private String provider;

}
