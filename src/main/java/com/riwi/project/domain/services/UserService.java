package com.riwi.project.domain.services;

import com.riwi.project.api.Exeptions.UnauthorizedException;
import com.riwi.project.api.controller.AuthController;
import com.riwi.project.api.dto.request.UserReq;
import com.riwi.project.api.dto.request.ValidationReq;
import com.riwi.project.api.dto.response.UserRes;
import com.riwi.project.api.utils.TransformUtil;
import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.repository.UserRepository;
import com.riwi.project.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Repository
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private TransformUtil transformUtil;

    @Autowired
    private ValidationReq validationReq;

    //Password encoder for better security
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public UserRes saveUser(UserReq user, String token)throws Exception{
        try{
            validationReq.isAdmin(token);
            user.setPassword(encoder.encode(user.getPassword()));
            UserEntity newUser = transformUtil.transformUserEntity(user);
            UserEntity savedUser = userRepo.save(newUser);
            return transformUtil.transformUserRes(savedUser);
        }catch (UnauthorizedException e){
            throw new UnauthorizedException("Only Administrator have the permission to create a new user");
        }

    }

    public List<UserEntity> getAllUsers(){
        return userRepo.findAll();
    }

    public UserEntity getUserById(Long id){
        return userRepo.findById(id).get();
    }

    public void deleteUserById(Long id){
        userRepo.deleteById(id);
    }

    public String verify(UserEntity user) {
        Authentication authentication =
                authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
        if (authentication.isAuthenticated()){
            return jwtService.generateToken(user.getUsername(),user.getRole().toString());
        }else {
            return "Unable to find user or password";
        }
    }

    public UserEntity getUserByUsername(String username){
        return userRepo.findByUsername(username);
    }
}
