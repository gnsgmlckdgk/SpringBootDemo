package com.hch.demo.enums;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ResCode {

    SUCCESS(200, "SUCCESS", HttpStatus.OK),
    FAIL(200, "FAIL", HttpStatus.OK),
    ERROR(500, "ERROR", HttpStatus.INTERNAL_SERVER_ERROR)
    ;

    private final int code;
    private final String msg;
    private final HttpStatus status;

    ResCode(int code, String msg, HttpStatus status) {
        this.code = code;
        this.msg = msg;
        this.status = status;
    }

}
