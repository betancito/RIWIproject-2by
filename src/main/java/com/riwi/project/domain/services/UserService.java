package com.riwi.project.domain.services;

import com.riwi.project.domain.model.UserEntity;
import com.riwi.project.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserService {
    @Autowired
    private UserRepository userRepo;

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

}
