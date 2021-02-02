package com.demo.quartz.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code= HttpStatus.FORBIDDEN)
public class PermissionException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private String message;

    public PermissionException(String infoError) {
        super(infoError);
        message = infoError;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
