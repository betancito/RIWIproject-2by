package com.riwi.project.api.controller;

import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    @Operation(description = "create user entities")
    public UserEntity register(@RequestBody UserEntity user){
        return userService.saveUser(user);
    }

    @PostMapping("/login")
    @Operation(description = "login dd")
    public String login(@Parameter String password, @Parameter String username){
        UserEntity user = new UserEntity();
        user.setPassword(password);
        user.setUsername(username);
        return  userService.verify(user);
    }
}
