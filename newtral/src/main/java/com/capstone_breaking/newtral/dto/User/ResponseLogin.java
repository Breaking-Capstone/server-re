package com.capstone_breaking.newtral.dto.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Schema(description = "토큰 확인용 DTO")
public class ResponseLogin {

    @Schema(description = "가입이 되어있는지 여부", example = "true")
    private Boolean isRegistered;

    @Schema(description = "access토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmciLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWQiOjEsInR5cGUiOiJhY2Nlc3MiLCJpYXQiOjE3MTI2NDc4MDMsImV4cCI6MTcxMjY1MTQwM30.-uafFJg0slWto4EBlR7wakCbrEndcpmWAeyUPty5m0A")
    private String access;

    @Schema(description = "refresh토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJzdHJpbmciLCJyb2xlcyI6WyJST0xFX1VTRVIiXSwiaWQiOjEsInR5cGUiOiJyZWZyZXNoIiwiaWF0IjoxNzEyNjQ3ODAzLCJleHAiOjE3MTI2NDg0MDN9.OjaQBD65ZxeTEor7hvjBw7EVY3R64ns1Eh60iaZfVNs")
    private String refresh;

    @Builder
    public ResponseLogin(Boolean isRegistered, String access, String refresh){
        this.isRegistered = isRegistered;
        this.access = access;
        this.refresh = refresh;
    }
}
