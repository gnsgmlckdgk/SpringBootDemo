package com.hch.demo.controller.api.v1;

import com.hch.demo.enums.KeyEnum;
import com.hch.demo.enums.MsgEnum;
import com.hch.demo.enums.ResCode;
import com.hch.demo.model.entity.User;
import com.hch.demo.model.response.BasicResponse;
import com.hch.demo.model.response.CommonResponse;
import com.hch.demo.model.response.ErrorResponse;
import com.hch.demo.model.value.UserValue;
import com.hch.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Basic;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="${demo.api}/users", produces = {MediaType.APPLICATION_JSON_VALUE})
@RestController
public class UserController {

    private final UserService userService;


    /**
     * id로 유저정보 조회
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity<? extends BasicResponse> select(@PathVariable("id") long id) {

        Optional<User> oUser = userService.findById(id);

        if(!oUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(MsgEnum.MSG_USER_NOT_FOUND.getMsg()));
        }

        return ResponseEntity.ok().body(new CommonResponse<User>(oUser.get()));
    }


//    /**
//     * id로 유저정보 조회
//     * 도메인 클래스 컨버터 기능으로 조회
//     * @param user
//     * @return
//     */
//    @GetMapping("/{id}")
//    public BasicResponse findById(@PathVariable("id") User user) {
//
//        if(user == null) {
//            return new ErrorResponse(MsgEnum.MSG_USER_NOT_FOUND.getMsg());
//        }
//
//        return new CommonResponse<User>(user);
//    }

    /**
     * 유저정보 저장
     * @param value
     * @return
     */
    @PostMapping("")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody UserValue value) {

        User user = userService.save(value);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(MsgEnum.MSG_USER_JOIN_FAIL.getMsg(), MsgEnum.MSG_USER_JOIN_FAIL.getCode()));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 유저정보 변경
     * @param id
     * @param value
     * @return
     */
    @PatchMapping("/{id}")
    public ResponseEntity<? extends BasicResponse> patch(@PathVariable("id") long id, @RequestBody UserValue value) {

        if(userService.patch(id, value) < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(MsgEnum.MSG_USER_PATCH_FAIL.getMsg(), MsgEnum.MSG_USER_PATCH_FAIL.getCode()));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 유저정보 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("id") long id) {

        if(userService.delete(id) < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(MsgEnum.MSG_USER_DEL_FAIL.getMsg(), MsgEnum.MSG_USER_DEL_FAIL.getCode()));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
