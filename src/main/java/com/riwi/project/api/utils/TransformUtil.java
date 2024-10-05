package com.riwi.project.api.utils;

import com.riwi.project.api.dto.request.UserReq;
import com.riwi.project.api.dto.response.UserRes;
import com.riwi.project.domain.model.UserEntity;
import org.apache.catalina.User;
import org.springframework.stereotype.Component;

@Component
public class TransformUtil {

//Utils to transform Users
    //to userEntity from UserRequest
    public UserEntity transformUserEntity(UserReq userReq){
        return UserEntity.builder()
                .id(userReq.getId())
                .username(userReq.getUsername())
                .email(userReq.getEmail())
                .password(userReq.getPassword())
                .role(userReq.getRole())
                .build();
    }

    //to userResponse from UserEntity
    public UserRes transformUserRes(UserEntity userEntity){
        return UserRes.builder()
                .email(userEntity.getEmail())
                .username(userEntity.getUsername())
                .role(userEntity.getRole())
                .build();
    }
}
