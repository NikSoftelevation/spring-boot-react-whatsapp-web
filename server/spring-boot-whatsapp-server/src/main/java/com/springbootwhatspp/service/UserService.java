package com.springbootwhatspp.service;

import com.springbootwhatspp.exception.UserException;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.request.UpdateUserRequest;

import java.util.List;

public interface UserService {
    public User createUser(User user);

    public User findUserByUserId(int userId);

    public User updateUser(int userId, UpdateUserRequest req) throws UserException;

    public User findUserProfile(String jwt);

    public List<User> searchUser(String query);
}
