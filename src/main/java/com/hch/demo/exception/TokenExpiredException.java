package com.hch.demo.exception;

public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super("Token이 만료되었습니다.");
    }

}
