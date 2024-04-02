package com.capstone_breaking.newtral.service;

import com.capstone_breaking.newtral.common.CommonResponse;
import com.capstone_breaking.newtral.common.JwtProvider;
import com.capstone_breaking.newtral.domain.User;
import com.capstone_breaking.newtral.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        OAuth2CustomUser oAuth2User = (OAuth2CustomUser) authentication.getPrincipal();
        ObjectMapper objectMapper = new ObjectMapper();

        String email = oAuth2User.getEmail(); // OAuth2User로부터 Resource Owner의 이메일 주소를 얻음 객체로부터
        List<String> authorities = List.of("ROLE_USER");           // 권한 정보 생성
        authorities.stream().map(authoritie -> authoritie.replaceFirst("a", "")).collect(Collectors.toList());

        Map<String, String > map = new HashMap<>();

        map.put("refreshToken", jwtProvider.createRefreshToken(email, authorities));
        map.put("accessToken", jwtProvider.creteAccessToken(email, authorities));
        response.getWriter().write(objectMapper.writeValueAsString(new CommonResponse("OK", map)));
        response.getWriter().flush();

    }

}