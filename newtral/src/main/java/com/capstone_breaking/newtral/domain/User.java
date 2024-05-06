package com.capstone_breaking.newtral.domain;

import com.capstone_breaking.newtral.common.Time;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    private String provider;

    @Column(name="refresh_token")
    private String refreshToken;

    //프론트에서 직접 입력받을 정보
    private Long age;

    private Long reliabilityPercent;

    @OneToMany(mappedBy = "user")
    private List<UserCategory> interests = new ArrayList<>();


    public User update(String name, String profileImageUrl) {
        this.name = name;
        this.profileImage = profileImageUrl;

        return this;
    }

    public User editRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;

        return this;
    }

    public User editReliabilityPercent(Long reliabilityPercent){
        this.reliabilityPercent = reliabilityPercent;
        return this;
    }

    //builder 패턴, toDto
    @Builder
    public User(String email, String name, List<String> role, String profileImage, String provider, Long reliabilityPercent){
        this.reliabilityPercent = reliabilityPercent;
        this.email = email;
        this.name = name;
        this.role = role;
        this.provider =provider;
        this.profileImage = profileImage;
    }
}
