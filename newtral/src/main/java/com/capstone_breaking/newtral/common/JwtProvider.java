package com.capstone_breaking.newtral.common;

import com.capstone_breaking.newtral.common.ex.JwtEmptyException;
import com.capstone_breaking.newtral.common.ex.MismatchTokenTypeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
@RequiredArgsConstructor
public class JwtProvider {

    @Value("${spring.jwt.secret}")
    private String secretKey;

    @Value("${spring.jwt.refresh-token-valid-time}")
    private Long refreshTokenValidTime;

    @Value("${spring.jwt.access-token-valid-time}")
    private Long accessTokenValidTime;

    private final CustomUserDetailsService customUserDetailsService;

    @PostConstruct
    protected void init() {
        log.info("[init] 시크릿키 초기화 시작");
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes(StandardCharsets.UTF_8));
        log.info("[init] 시크릿키 초기화 성공");
    }

    public String createRefreshToken(String email,Long id ,List<String> roles) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        claims.put("id", id);
        claims.put("type", "refresh");
        Date now = new Date();
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + refreshTokenValidTime))//유효시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String creteAccessToken(String email,Long id ,List<String> roles){
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles);
        claims.put("id", id);
        claims.put("type", "access");
        Date now = new Date();
        return Jwts
                .builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + accessTokenValidTime))//유효시간
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();

    }

    public Authentication getAuthentication(String token) {
        log.info("[getAuthentication] 토큰 인증 정보 조회 시작");
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(this.getMemberEmail(token));
        log.info("[getAuthentication] 토큰 인증 정보 조회 완료, UserDetails UserName : {}",
                userDetails.getUsername());
        return new UsernamePasswordAuthenticationToken(userDetails, "",
                userDetails.getAuthorities());
    }


    private String getMemberEmail(String token) {
        log.info("[getMemberEmail] 토큰 기반 회원 구별 정보 추출");
        String email = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        log.info("[getMemberEmail] 토큰 기반 회원 구별 정보 추출 완료, info : {}", email);
        return email;
    }


    public Long getUserId(String token) {
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출");
        Long memberId = Long.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id").toString());
        log.info("[getUsername] 토큰 기반 회원 구별 정보 추출 완료, info : {}", memberId);
        return memberId;
    }

    public Boolean validAccessToken(String token) {
        log.info("값: {}", Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("type").equals("access"));
        if (Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("type").equals("access")) {
            return true;
        } else {
            throw new MismatchTokenTypeException(ExceptionMessage.TOKEN_TYPE_INVALID);
        }
    }

    public Boolean validRefreshToken(String token) {
        log.info("값: {}", Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("type").equals("refresh"));
        if (Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("type").equals("refresh")) {
            return true;
        } else {
            throw new MismatchTokenTypeException(ExceptionMessage.TOKEN_TYPE_INVALID);
        }
    }

    public String getAuthorizationToken(HttpServletRequest request) {
        log.info("[getAuthorizationToken] HTTP 헤더에서 Token 값 추출");

        return request.getHeader("Authorization");
    }

    public String resolveServiceToken(HttpServletRequest request) {
        log.info("[resolveServiceToken] HTTP 헤더에서 Token 값 추출");

        String token = request.getHeader("Authorization");

        if (token == null) {
            throw new JwtEmptyException(ExceptionMessage.TOKEN_NOT_FOUND);
        } else {
            return token.substring(7);
        }
    }

    public boolean validDateToken(String token) {
        log.info("[validateToken] 토큰 유효 체크 시작");
        Jws<Claims> claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);

        if (!claims.getBody().getExpiration().before(new Date())) {
            log.info("[validDateToken] 토큰 유효성 체크 성공");
            return true;
        } else {
            log.info("[validDateToken] 토큰 유효성 체크 실패");
            return false;
        }
    }

}