package com.capstone_breaking.newtral.controller;

import com.capstone_breaking.newtral.common.CommonResponse;
import com.capstone_breaking.newtral.dto.RequestInterest;
import com.capstone_breaking.newtral.dto.User.RequestUser;
import com.capstone_breaking.newtral.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("")
public class UserController {

    private final UserService userService;
    @GetMapping("/health-check")
    public ResponseEntity<Void> checkHealthStatus() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/users/register")
    public ResponseEntity<CommonResponse> registerUser(@RequestBody RequestUser requestUser){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CommonResponse("OK", userService.registerUser(requestUser)));
    }

    @PatchMapping("/users/interest")
    public ResponseEntity<CommonResponse> setUserInterest(@RequestBody RequestInterest requestInterest,
                                                          @AuthenticationPrincipal UserDetails userDetails){
        userService.setUserInterest(requestInterest, userDetails);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new CommonResponse("OK", null));
    }

}
