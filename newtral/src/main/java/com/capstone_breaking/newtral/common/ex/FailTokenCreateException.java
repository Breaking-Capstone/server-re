package com.capstone_breaking.newtral.common.ex;

import com.capstone_breaking.newtral.common.ExceptionMessage;

public class FailTokenCreateException extends RuntimeException {

    public FailTokenCreateException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }

}
