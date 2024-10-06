package com.riwi.project.api.controller;

import com.riwi.project.api.dto.request.UserReq;
import com.riwi.project.api.dto.response.UserRes;
import com.riwi.project.domain.enums.UserRole;
import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/auth")
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(description = "login endpoint")
    @ApiResponse(responseCode = "200", description = "User logged in successfully")
    @ApiResponse(responseCode = "400", description = "Unable to log in user")
    public String login(@Parameter String password, @Parameter String username){
        UserEntity user = new UserEntity();
        user.setPassword(password);
        user.setUsername(username);
        UserEntity user1 = userService.getUserByUsername(username);
        if (user1 == null) {
            throw new IllegalArgumentException("Usuario no encontrado: " + username);
        }
        user.setRole(user1.getRole());
        return userService.verify(user);
    }

    @PostMapping("/register")
    @Operation(description = "Public Register endpoint")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    @ApiResponse(responseCode = "400", description = "Unable to create user")
    public UserRes createUser(@Parameter String username,
                              @Parameter String password,
                              @Parameter String email){
        UserReq user = new UserReq();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(UserRole.USER);
        return userService.saveUser(user);
    }
}
