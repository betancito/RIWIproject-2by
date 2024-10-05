package com.riwi.project.api.controller;

import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.repository.UserRepository;
import com.riwi.project.domain.services.MyUserDetailsService;
import com.riwi.project.domain.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(description = "login dd")
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
}
