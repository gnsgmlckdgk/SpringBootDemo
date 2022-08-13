package com.hch.demo.exception;


import com.hch.demo.model.response.BasicResponse;
import com.hch.demo.model.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionAdvice {

    @ExceptionHandler(AuthorizationHeaderNotExistsException.class)
    protected ResponseEntity<BasicResponse> handleMethodAuthorizationHeaderNotExistsException(AuthorizationHeaderNotExistsException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        final BasicResponse response = new ErrorResponse(status.value() +"", e.getMessage());
        return new ResponseEntity<>(response, status);
    }

    @ExceptionHandler(InvalidTokenException.class)
    protected ResponseEntity<BasicResponse> handleMethodInvalidTokenException(InvalidTokenException e) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        final BasicResponse response = new ErrorResponse(status.value() +"", e.getMessage());
        return new ResponseEntity<>(response, status);
    }
}
