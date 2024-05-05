package com.capstone_breaking.newtral.common.ex;

import com.capstone_breaking.newtral.common.ExceptionMessage;

public class JwtEmptyException extends RuntimeException {
    public JwtEmptyException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }
}
