package com.hch.demo.enums;

import lombok.Getter;

@Getter
public enum MsgEnum {

    /**
     * 회원정보 메시지
     */
    MSG_USER_NOT_FOUND("일치하는 회원 정보가 없습니다. 사용자 id를 확인해주세요.", "404"),
    MSG_USER_JOIN_FAIL("회원가입 실패", "500"),
    MSG_USER_PATCH_FAIL("회원정보 변경 실패", "404"),
    MSG_USER_DEL_FAIL("회원정보 삭제 실패", "404")
    ;

    private final String msg;
    private final String code;

    MsgEnum(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

}
