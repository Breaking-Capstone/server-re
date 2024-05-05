package com.capstone_breaking.newtral.common.ex;

import com.capstone_breaking.newtral.common.ExceptionMessage;
import org.aspectj.weaver.ast.Not;

public class NotFoundElementException extends RuntimeException {

    public NotFoundElementException(ExceptionMessage exceptionMessage){
        super(exceptionMessage.getMessage());
    }

}
