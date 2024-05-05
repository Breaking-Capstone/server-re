package com.capstone_breaking.newtral.common;

import com.capstone_breaking.newtral.common.ex.FailTokenCreateException;
import com.capstone_breaking.newtral.common.ex.NotFoundElementException;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(value = NotFoundElementException.class)
    public ResponseEntity<CommonResponse> handleNotFoundElementException(NotFoundElementException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new CommonResponse<>(ex.getMessage(), null));

    }

    @ExceptionHandler(value = FailTokenCreateException.class)
    public ResponseEntity<CommonResponse> handleTokenCreateError(FailTokenCreateException ex) {
        log.error(ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CommonResponse(ex.getMessage(), null));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<CommonResponse> handleExpiredJwtException(ExpiredJwtException e) {
        log.error("[ExpiredJwtException] 토큰에러");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new CommonResponse(e.getMessage(), null));
    }

}
