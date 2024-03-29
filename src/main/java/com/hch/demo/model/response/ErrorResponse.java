package com.hch.demo.model.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorResponse extends BasicResponse{

    private String errorCode;
    private String errorMessage;

    public ErrorResponse(String errorMessage) {
        this.errorMessage = errorMessage;
        this.errorCode = "404";
    }
    public ErrorResponse(String errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
