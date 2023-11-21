package ru.kata.spring.rest.demo.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserGlobalExceptionHandler {

    //если пользователь не будет найден
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handelException (UserNotFoundException ex) {
        UserErrorResponse response = new UserErrorResponse(ex.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    //если в URL будет передан любой текст вместо id
    //ошибка запроса
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handelException (Exception ex) {
        UserErrorResponse response = new UserErrorResponse(ex.getMessage(), System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //при ошибке создания пользователя
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handelException (UserNotCreatedException ex) {
        UserErrorResponse response = new UserErrorResponse(ex.getMessage(),System.currentTimeMillis());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}
