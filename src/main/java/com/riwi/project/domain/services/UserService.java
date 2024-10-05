package com.riwi.project.domain.services;

import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.repository.UserRepository;
import com.riwi.project.infrastructure.security.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserService {
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authManager;

    //Password encoder for better security
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public UserEntity saveUser(UserEntity user){
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepo.save(user);
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
            return jwtService.generateToken(user.getUsername());
        }else {
            return "Unable to find user or password";
        }
    }
}
