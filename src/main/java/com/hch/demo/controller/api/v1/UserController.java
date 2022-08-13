package com.hch.demo.controller.api.v1;

import com.hch.demo.enums.MsgEnum;
import com.hch.demo.model.entity.User;
import com.hch.demo.model.response.BasicResponse;
import com.hch.demo.model.response.CommonResponse;
import com.hch.demo.model.response.ErrorResponse;
import com.hch.demo.model.response.UserResponse;
import com.hch.demo.model.value.UserValue;
import com.hch.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Tag(name = "user", description = "사용자 API")
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원 조회 성공", content = @Content(schema = @Schema(implementation = UserResponse.class))),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = ErrorResponse.class))) })
    @Operation(summary = "회원조회", description = "id를 이용하여 회원정보를 조회합니다.")
    public ResponseEntity<? extends BasicResponse> select(
            @Parameter(name = "id", description = "user 의 id", in = ParameterIn.PATH) @PathVariable("id") long id) {

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
    @Operation(summary = "회원저장", description = "회원 정보를 저장합니다.")
    public ResponseEntity<? extends BasicResponse> save(@RequestBody UserValue value) {

        User user = userService.save(value);
        if(user == null) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(MsgEnum.MSG_USER_JOIN_FAIL.getCode(), MsgEnum.MSG_USER_JOIN_FAIL.getMsg()));
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
    @Operation(summary = "회원정보변경", description = "id를 이용하여 회원정보를 변경합니다.")
    public ResponseEntity<? extends BasicResponse> patch(@PathVariable("id") long id, @RequestBody UserValue value) {

        if(userService.patch(id, value) < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(MsgEnum.MSG_USER_PATCH_FAIL.getCode(), MsgEnum.MSG_USER_PATCH_FAIL.getMsg()));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    /**
     * 유저정보 삭제
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "회원삭제", description = "id를 이용하여 회원정보를 삭제합니다.")
    public ResponseEntity<? extends BasicResponse> delete(@PathVariable("id") long id) {

        if(userService.delete(id) < 1) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse(MsgEnum.MSG_USER_DEL_FAIL.getCode(), MsgEnum.MSG_USER_DEL_FAIL.getMsg()));
        }

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
