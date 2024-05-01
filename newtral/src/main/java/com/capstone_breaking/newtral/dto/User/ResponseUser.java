package com.capstone_breaking.newtral.dto.User;

import com.capstone_breaking.newtral.domain.UserCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.springframework.aot.hint.MemberCategory;

import java.util.List;

@Getter
public class ResponseUser {

    @Schema(description = "유저의 이메일", example = "sseeddff@kakao.com")
    private String email;

    @Schema(description = "유저 이름 혹은 닉네임", example = "깨구리")
    private String name;

    @Schema(description = "유저의 프로필이미지", example = "프로필 이미지 링크.")
    private String profileImage;

    @Schema(description = "OAauth2 제공사", example = "kakao")
    private String provider;

    @Schema(description = "유저 나이", example = "32")
    private Long age;

    @Schema(description = "유저 관심사")
    private List<String> interests;


    @Builder
    public ResponseUser(String email, String name, String profileImage, String provider, Long age, List<String> interests){
        this.email = email;
        this.name = name;
        this.profileImage = profileImage;
        this.provider = provider;
        this.age = age;
        this.interests = interests;
    }

}
