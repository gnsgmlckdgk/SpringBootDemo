package com.hch.demo.enums;

import lombok.Getter;

@Getter
public enum TimeEnum {

    TZ_UTC("UTC", 0),
    TZ_ASIA_SEOUAL("Asia/Seoul", 9)
    ;


    private final String val;       // 타임존
    private final int timeDiff;     // 시간차

    TimeEnum(String val, int timeDiff) {
        this.val = val;
        this.timeDiff = timeDiff;
    }

}
