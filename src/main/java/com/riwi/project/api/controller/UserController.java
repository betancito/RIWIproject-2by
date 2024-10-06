package com.riwi.project.api.controller;

import com.riwi.project.api.dto.request.UserPublicReq;
import com.riwi.project.api.dto.request.UserReq;
import com.riwi.project.api.dto.response.UserRes;
import com.riwi.project.domain.enums.UserRole;
import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/createUser")
    @Operation(summary = "Register User", description = "Endpoint to create an user")
    @ApiResponse(responseCode = "201", description = "User created successfully")
    public UserRes Register(@Parameter String username,
                            @Parameter String email,
                            @Parameter String password,
                            @Parameter UserRole role,
                            @Parameter(hidden = true)@RequestHeader("Authorization")String token) throws Exception {
        UserReq user = new UserReq();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        user.setRole(role);
        return userService.saveUser(user);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find User", description = "Gets an user by using the id number")
    @ApiResponse(responseCode = "200", description = "User found successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public UserRes GetById(@PathVariable Long id,
                                             @Parameter(hidden = true)@RequestHeader("Authorization")String token){

        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an user", description = "Deletes an user by using the id")
    @ApiResponse(responseCode = "200", description = "User deleted successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public void Delete(@PathVariable Long id){
        userService.deleteUserById(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates an user", description = "Updates an user by using the id")
    @ApiResponse(responseCode = "200", description = "User updated successfully")
    @ApiResponse(responseCode = "404", description = "User not found")
    public UserRes Update(@PathVariable Long id, @RequestBody UserPublicReq user){
        return userService.updateUser(id, user);
    }
}
