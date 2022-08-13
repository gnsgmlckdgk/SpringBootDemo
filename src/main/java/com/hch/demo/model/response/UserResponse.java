package com.hch.demo.model.response;

import com.hch.demo.model.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원 Response")
public class UserResponse extends CommonResponse<User> {
    public UserResponse(User data) {
        super(data);
    }
}
