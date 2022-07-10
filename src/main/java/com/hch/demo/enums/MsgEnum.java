package com.hch.demo.enums;

import lombok.Getter;

@Getter
public enum MsgEnum {

    /**
     * 회원정보 메시지
     */
    MSG_USER_NOT_FOUND("일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요."),
    MSG_USER_JOIN_FAIL("회원 가입 실패")
    ;

    private final String msg;

    MsgEnum(String msg) {
        this.msg = msg;
    }

}
