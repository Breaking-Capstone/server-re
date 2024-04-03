package com.capstone_breaking.newtral.domain;

import com.capstone_breaking.newtral.common.Time;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    //oauth2 서비스에서 받아올 정보
    private String email;

    @Column(name="user_name")
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Column(nullable = false)
    private List<String> role;

    private String profileImage;

    @Column(name="refresh_token")
    private String refreshToken;

    private String provider;

    private Long age;

    public User update(String name, String profileImageUrl) {
        this.name = name;
        this.profileImage = profileImageUrl;

        return this;
    }

    //builder 패턴, toDto
    @Builder
    public User(String email, String name, List<String> role, String profileImage, String provider){
        this.email = email;
        this.name = name;
        this.role = role;
        this.provider =provider;
        this.profileImage = profileImage;
    }
}
