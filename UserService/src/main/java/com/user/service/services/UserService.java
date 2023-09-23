package com.user.service.services;

import com.user.service.entities.User;

import java.util.List;

public interface UserService {
    // create user
    User saveUser(User user);

    // get all users
    List<User> getAllUsers();

    // get user by id
    User getUser(String userId);

    // update user
    User updateUser(User user);

    // delete user
    void deleteUser(String userId);
}
