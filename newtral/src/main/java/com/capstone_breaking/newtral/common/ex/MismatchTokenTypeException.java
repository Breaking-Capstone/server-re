package com.capstone_breaking.newtral.common.ex;

import com.capstone_breaking.newtral.common.ExceptionMessage;

public class MismatchTokenTypeException extends RuntimeException {

    public MismatchTokenTypeException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }

}
