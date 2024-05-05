package com.capstone_breaking.newtral.service;

import com.capstone_breaking.newtral.common.CustomUserDetails;
import com.capstone_breaking.newtral.common.ExceptionMessage;
import com.capstone_breaking.newtral.common.JwtProvider;
import com.capstone_breaking.newtral.common.ex.FailTokenCreateException;
import com.capstone_breaking.newtral.common.ex.NotFoundElementException;
import com.capstone_breaking.newtral.domain.Category;
import com.capstone_breaking.newtral.domain.User;
import com.capstone_breaking.newtral.domain.UserCategory;
import com.capstone_breaking.newtral.dto.CategoryList;
import com.capstone_breaking.newtral.dto.RequestInterest;
import com.capstone_breaking.newtral.dto.User.RequestUser;
import com.capstone_breaking.newtral.dto.User.ResponseLogin;
import com.capstone_breaking.newtral.dto.User.ResponseUser;
import com.capstone_breaking.newtral.dto.User.ResponseToken;
import com.capstone_breaking.newtral.repository.CategoryRepository;
import com.capstone_breaking.newtral.repository.UserCategoryRepository;
import com.capstone_breaking.newtral.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final UserCategoryRepository userCategoryRepository;
    private final JwtProvider jwtProvider;

    public ResponseLogin registerUser(RequestUser requestUser){
        Optional<User> user = userRepository.findByEmail(requestUser.getEmail());
        User returnUser;
        Boolean isRegistered;

        if(user.isPresent()){
            returnUser = user.get().update(requestUser.getName(), requestUser.getProfileImage());
            isRegistered = true;
        }
        else{
            returnUser = User.builder()
                    .name(requestUser.getName())
                    .email(requestUser.getEmail())
                    .role(List.of("ROLE_USER"))
                    .profileImage(requestUser.getProfileImage())
                    .provider(requestUser.getProvider())
                    .build();
            isRegistered = false;
        }
        userRepository.save(returnUser);

        String accessToken = jwtProvider.creteAccessToken(returnUser.getEmail(), returnUser.getId(), returnUser.getRole());
        String refreshToken = jwtProvider.createRefreshToken(returnUser.getEmail(), returnUser.getId(), returnUser.getRole());

        returnUser.editRefreshToken(refreshToken);
        userRepository.save(returnUser);

        log.info("access: {}", accessToken);
        log.info("refresh: {}", refreshToken);

        ResponseLogin responseLogin = ResponseLogin.builder()
                .isRegistered(isRegistered)
                .access(accessToken)
                .refresh(refreshToken)
                .build();

        return responseLogin;
    }

    @Transactional
    public Boolean checkInterest(UserDetails userDetails){
        Long userId = ((CustomUserDetails) userDetails).getId();
        log.info("유저 아이디 = {}", userId);
        User user = userRepository.findById(userId).get();

        if(user.getInterests().isEmpty()){
            return false;
        }
        return true;
    }

    public void setUserInterest(RequestInterest requestInterest, UserDetails userDetails){
        Long userId = ((CustomUserDetails) userDetails).getId();
        log.info("유저 아이디 = {}", userId);
        User user = userRepository.findById(userId).get();

        userCategoryRepository.deleteAll(userCategoryRepository.findUserCategoryByUserId(userId));

        for(String category : requestInterest.getInterests()){
            log.info("카테고리: {}", category);
            Category getCategory = categoryRepository.findById(CategoryList.valueOf(category).getId())
                    .orElseThrow(()->new NotFoundElementException(ExceptionMessage.ELEMENT_NOT_FOUND));
                UserCategory userCategory = UserCategory.builder()
                        .category(getCategory)
                        .user(user)
                        .build();

                userCategoryRepository.save(userCategory);
        }
    }

    @Transactional(readOnly = true)
    public ResponseUser getUserStatus(UserDetails userDetails){
        Long userId = ((CustomUserDetails) userDetails).getId();
        log.info("유저 아이디 = {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(()->new RuntimeException("유저가 존재하지 않음"));

        ResponseUser responseUser = ResponseUser.builder()
                .name(user.getName())
                .age(user.getAge())
                .provider(user.getProvider())
                .interests(user.getInterests().stream().map(interests -> interests.getCategory().getCategoryName()).toList())
                .email(user.getEmail())
                .profileImage(user.getProfileImage())
                .build();

        return responseUser;
    }

    public void deleteUser(String userId){
        User user = userRepository.findByEmail(userId).get();

        userRepository.delete(user);
    }

    public ResponseToken reissueAccessToken(HttpServletRequest request) {

        String token = jwtProvider.resolveServiceToken(request);

        Long userId = jwtProvider.getUserId(token);
        log.info("[reissueAccessToken] memberId 추출 성공. memberId = {}", userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundElementException(ExceptionMessage.MEMBER_NOTFOUND));
        log.info("[reissueAccessToken] member 찾기 성공. memberEmail = {}", user.getEmail());

        String refreshToken = user.getRefreshToken();

        //refreshToken 의 유효 시간과, Header 에 담겨 온 RefreshToken 과 redis 에 저장되어있는 RefreshToken 과 일치하는지 비교한다.

        if (!refreshToken.equals(token)) {
            throw new FailTokenCreateException(ExceptionMessage.REFRESHTOKEN_NOT_SAME);
        }

        String accessToken = jwtProvider.creteAccessToken(user.getEmail(), userId, user.getRole());
        log.info("[reissueAccessToken] accessToken 새로 발급 성공: {}", accessToken);

        String newRefreshToken = jwtProvider.createRefreshToken(user.getEmail(), userId, user.getRole());
        log.info("[reissueAccessToken] refreshToken 새로 발급 성공: {}", newRefreshToken);

        ResponseToken responseToken = ResponseToken.builder()
                .refreshToken(newRefreshToken)
                .accessToken(accessToken)
                .build();

        //redis 에 토큰 저장
        userRepository.save(user.editRefreshToken(newRefreshToken));

        return responseToken;
    }

}
