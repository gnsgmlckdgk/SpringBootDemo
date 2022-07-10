package com.hch.demo.enums;

import lombok.Getter;

@Getter
public enum KeyEnum {

    RES_RESULT("result"),
    RES_MSG("reason")
    ;

    private final String key;

    KeyEnum(String key) {
        this.key = key;
    }

}
