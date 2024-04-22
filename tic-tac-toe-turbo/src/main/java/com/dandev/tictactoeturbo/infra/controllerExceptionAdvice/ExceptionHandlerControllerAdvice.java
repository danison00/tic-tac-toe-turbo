package com.dandev.tictactoeturbo.infra.controllerExceptionAdvice;

import com.dandev.tictactoeturbo.infra.exceptions.InvalidUsernameOrPassword;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler(InvalidUsernameOrPassword.class)
    public ResponseEntity<?> invalidUsernameOrPassword() {
        return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


}
