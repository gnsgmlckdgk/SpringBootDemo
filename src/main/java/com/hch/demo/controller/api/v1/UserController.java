package com.hch.demo.controller.api.v1;

import com.hch.demo.enums.KeyEnum;
import com.hch.demo.enums.MsgEnum;
import com.hch.demo.enums.ResCode;
import com.hch.demo.model.entity.User;
import com.hch.demo.model.value.UserValue;
import com.hch.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="${demo.api}/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class UserController {

    private final UserService userService;



    /**
     * id로 유저정보 조회
     * 도메인 클래스 컨버터 기능으로 조회
     * @param user
     * @return
     */
    @GetMapping("/{id}")
    public Map<String, Object> findById(@PathVariable("id") User user) {

        Map<String, Object> response = new HashMap<>();

        if(user != null) {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.SUCCESS.getMsg());
            response.put("user", user);

        } else {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.FAIL.getMsg());
            response.put(KeyEnum.RES_MSG.getKey(), MsgEnum.MSG_USER_NOT_FOUND);
        }

        return response;
    }

    /**
     * 유저정보 저장
     * @param value
     * @return
     */
    @PostMapping("")
    public Map<String, Object> save(@RequestBody UserValue value) {

        Map<String, Object> response = new HashMap<>();

        User user = userService.save(value);
        if(user != null) {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.SUCCESS.getMsg());
            response.put("user", user);
        } else {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.FAIL.getMsg());
            response.put(KeyEnum.RES_MSG.getKey(), MsgEnum.MSG_USER_JOIN_FAIL);
        }

        return response;
    }

    /**
     * 유저정보 변경
     * @param id
     * @param value
     * @return
     */
    @PatchMapping("/{id}")
    public Map<String, Object> patch(@PathVariable("id") long id, @RequestBody UserValue value) {
        Map<String, Object> response = new HashMap<>();

        if(userService.patch(id, value) > 0) {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.SUCCESS.getMsg());
        } else {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.FAIL.getMsg());
            response.put(KeyEnum.RES_MSG.getKey(), MsgEnum.MSG_USER_NOT_FOUND);
        }

        return response;
    }

    /**
     * 유저정보 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Map<String, Object> delete(@PathVariable("id") long id) {
        Map<String, Object> response = new HashMap<>();

        if(userService.delete(id) > 0) {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.SUCCESS.getMsg());
        } else {
            response.put(KeyEnum.RES_RESULT.getKey(), ResCode.FAIL.getMsg());
            response.put(KeyEnum.RES_MSG.getKey(), MsgEnum.MSG_USER_NOT_FOUND);
        }

        return response;
    }

}
