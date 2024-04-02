package com.capstone_breaking.newtral.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;


@Getter
public class CommonResponse<T> {

    private final String msg;

    private final T data;

    public CommonResponse(String msg, T data){
        this.msg = msg;
        this.data = data;
    }

}