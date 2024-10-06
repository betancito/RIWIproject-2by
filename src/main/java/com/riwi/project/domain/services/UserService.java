package com.riwi.project.domain.services;

import com.riwi.project.api.dto.request.UserPublicReq;
import com.riwi.project.api.dto.request.UserReq;
import com.riwi.project.api.dto.request.ValidationReq;
import com.riwi.project.api.dto.response.UserRes;
import com.riwi.project.api.utils.TransformUtil;
import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.repository.UserRepository;
import com.riwi.project.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.security.PublicKey;
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

    public UserRes saveUser(UserReq user){
            user.setPassword(encoder.encode(user.getPassword()));
            UserEntity newUser = transformUtil.transformUserEntity(user);
            UserEntity savedUser = userRepo.save(newUser);
            return transformUtil.transformUserRes(savedUser);
    }

    public List<UserEntity> getAllUsers(){
        return userRepo.findAll();
    }

    public UserRes getUserById(Long id){
        return transformUtil.transformUserRes(userRepo.findById(id).get());
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

    public UserRes updateUser(Long id, UserPublicReq userRequest){
        UserEntity updatedUser = userRepo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (updatedUser != null) {
            updatedUser.setUsername(userRequest.getUsername());
            updatedUser.setPassword(encoder.encode(userRequest.getPassword()));
            updatedUser.setEmail(userRequest.getEmail());
            userRepo.save(updatedUser);
            return transformUtil.transformUserRes(updatedUser);
        }
        return null;
    }
}
