package com.capstone_breaking.newtral.controller;

import com.capstone_breaking.newtral.common.CommonResponse;
import com.capstone_breaking.newtral.dto.Article.ResponseArticleForAIForm;
import com.capstone_breaking.newtral.dto.Article.ResponseInterestCategoryArticle;
import com.capstone_breaking.newtral.dto.RequestInterest;
import com.capstone_breaking.newtral.dto.User.RequestUser;
import com.capstone_breaking.newtral.dto.User.ResponseLogin;
import com.capstone_breaking.newtral.dto.User.ResponseToken;
import com.capstone_breaking.newtral.dto.User.ResponseUser;
import com.capstone_breaking.newtral.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {

    private final UserService userService;
    @GetMapping("/health-check")
    @Operation(summary = "건들지마라.", description = "aws에서 상태체크용으로 쓰는 api")
    public ResponseEntity<Void> checkHealthStatus() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/register")
    @Operation(summary = "회원가입", description = "서비스의 꽃은 회원가입! 제일 구현하기 힘들죠? ㄹㅇㅋㅋ <br><br> 입력:<br> RequestUser(DTO)<br><br> 출력:<br> ResponseLongin(DTO)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공", content = @Content(schema = @Schema(implementation = ResponseLogin.class))),
    })
    public ResponseEntity<CommonResponse> registerUser(@RequestBody RequestUser requestUser){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse("OK", userService.registerUser(requestUser)));
    }

    @PatchMapping("/users/interest")
    @Operation(summary = "유저 흥미 설정하기",description = "이거고친다고 오래걸렸다. 젠장. <br><br> 입력:<br> RequestInterest(DTO) <br><br> 출력:<br> null <br><br><br> 현재 가능한 흥미들: business, entertainment, general, health, science, sports, technology")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "유저 흥미 설정 성공", content = @Content(schema = @Schema(implementation = RequestInterest.class))),
    })
    public ResponseEntity<CommonResponse> setUserInterest(@RequestBody RequestInterest requestInterest,
                                                          @AuthenticationPrincipal UserDetails userDetails){
        userService.setUserInterest(requestInterest, userDetails);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", null));
    }

    @GetMapping("/users/status")
    @Operation(summary = "유저 정보 불러오기", description = "유저 정보 다불러옴 <br><br> 입력: <br> 필요음슴(토큰이나 넣어라 인간!) <br><br> 출력: <br> ResponseUser")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데이터베이스에서 뉴스 받아오기 성공", content = @Content(schema = @Schema(implementation = ResponseUser.class))),
    })
    public ResponseEntity<CommonResponse> getUserStatus(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", userService.getUserStatus(userDetails)));
    }

    @GetMapping("/users/interest-check")
    @Operation(summary = "유저가 흥미를 설정했나안햇나 확인", description = "유저가 흥미 설정 했는지 안했는지 확인 <br><br> 입력: <br> 필요음슴(토큰이나 넣어라 인간!) <br><br> 출력: <br> 흥미 설정 했으면 true 안했으면 false")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "데이터베이스에서 뉴스 받아오기 성공", content = @Content(schema = @Schema(implementation = ResponseInterestCategoryArticle.class))),
    })
    public ResponseEntity<CommonResponse> userInterestNullCheck(@AuthenticationPrincipal UserDetails userDetails){
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", userService.checkInterest(userDetails)));
    }

    @DeleteMapping("/users/delete")
    public ResponseEntity<CommonResponse> deleteUser(String userId){
        userService.deleteUser(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", null));
    }
    @GetMapping(value = "/users/auth/reissue")
    @Operation(summary = "토큰 재발급", description = "refreshToken과 accessToken을 재발급합니다. RefreshToken을 Authorization 헤더에 넣어주면 유효성검사 후 둘 다 재발급합니다.(RefreshToken의 유효기간을 늘리기 위해)")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "accessToken과 RefreshToken 재발급 성공", content = @Content(schema = @Schema(implementation = ResponseToken.class))),
    })
    public ResponseEntity<CommonResponse> reissueAccessToken(HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.OK).body(new CommonResponse("토큰 재발급 성공", userService.reissueAccessToken(request)));
    }
}
