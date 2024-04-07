package com.capstone_breaking.newtral.service;

import com.capstone_breaking.newtral.common.CustomUserDetails;
import com.capstone_breaking.newtral.common.JwtProvider;
import com.capstone_breaking.newtral.domain.Category;
import com.capstone_breaking.newtral.domain.User;
import com.capstone_breaking.newtral.domain.UserCategory;
import com.capstone_breaking.newtral.dto.CategoryList;
import com.capstone_breaking.newtral.dto.RequestInterest;
import com.capstone_breaking.newtral.dto.User.RequestUser;
import com.capstone_breaking.newtral.repository.CategoryRepository;
import com.capstone_breaking.newtral.repository.UserCategoryRepository;
import com.capstone_breaking.newtral.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final JwtProvider jwtProvider;
    public Map<String, String> registerUser(RequestUser requestUser){
        Optional<User> user = userRepository.findByEmail(requestUser.getEmail());

        User returnUser;

        String isRegister;
        if(user.isEmpty()){
            returnUser = User.builder()
                    .name(requestUser.getName())
                    .email(requestUser.getEmail())
                    .role(List.of("ROLE_USER"))
                    .profileImage(requestUser.getProfileImage())
                    .build();
            isRegister = "false";
        }
        else{
            returnUser = user.get().update(requestUser.getName(), requestUser.getProfileImage());
            isRegister = "true";
        }
        Map<String, String> tokens = new HashMap<>();
        userRepository.save(returnUser);

        String accessToken = jwtProvider.creteAccessToken(requestUser.getEmail(), returnUser.getId(), returnUser.getRole());
        String refreshToken = jwtProvider.createRefreshToken(requestUser.getEmail(), returnUser.getId(), returnUser.getRole());

        returnUser.editRefreshToken(refreshToken);
        userRepository.save(returnUser);

        log.info("access: {}", accessToken);
        log.info("refresh: {}", refreshToken);

        tokens.put("isRegister", isRegister);
        tokens.put("Access", accessToken);
        tokens.put("Refresh", refreshToken);

        return tokens;
    }

    public void setUserInterest(RequestInterest requestInterest, UserDetails userDetails){
        Long userId = ((CustomUserDetails) userDetails).getId();
        User user = userRepository.findById(userId).get();

        for(String category : requestInterest.getInterests()){
            Category getCategory = categoryRepository.findById(CategoryList.valueOf(category).getId()).get();

            UserCategory userCategory = UserCategory.builder()
                    .category(getCategory)
                    .user(user)
                    .build();

            userCategoryRepository.save(userCategory);

        }
    }
}
