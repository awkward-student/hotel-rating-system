package com.user.service.services.impl;

import com.user.service.entities.User;
import com.user.service.exceptions.ResourceNotFoundException;
import com.user.service.repositories.UserRepo;
import com.user.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User saveUser(User user) {
        // generate unique user Id.
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepo.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User getUser(String userId) {
        return userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User not found with id: "+userId));
    }

    @Override
    public User updateUser(User user) {
        User userUpdated = getUser(user.getUserId());
        userUpdated.setName(user.getName());
        userUpdated.setEmail(user.getEmail());
        userUpdated.setAbout(user.getAbout());
        return userUpdated;
    }

    @Override
    public void deleteUser(String userId) {
        this.userRepo.deleteById(userId);
    }
}
