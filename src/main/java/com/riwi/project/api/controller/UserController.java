package com.riwi.project.api.controller;

import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    @Operation(summary = "Register User", description = "Endpoint to create an user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    public UserEntity Register(@RequestHeader UserEntity user){
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    public UserEntity GetById(@PathVariable Long id,
                              @Parameter(hidden = true)@RequestHeader("Authorization")String token){
        return userService.getUserById(id);
    }
}
