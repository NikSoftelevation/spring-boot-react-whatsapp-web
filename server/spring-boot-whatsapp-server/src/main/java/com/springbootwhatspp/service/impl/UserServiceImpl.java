package com.springbootwhatspp.service.impl;

import com.springbootwhatspp.exception.UserException;
import com.springbootwhatspp.jwt.TokenProvider;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.request.UpdateUserRequest;
import com.springbootwhatspp.repository.UserRepository;
import com.springbootwhatspp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TokenProvider tokenProvider;

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User findUserByUserId(int userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserException("User not found with userId " + userId));
    }

    @Override
    public User updateUser(int userId, UpdateUserRequest req) throws UserException {
        User user = userRepository.findById(userId).orElseThrow(() -> new UserException("User not found with userId " + userId));

        if (req.getFull_name() != null) {
            user.setFull_name(req.getFull_name());
        }
        if (req.getProfile_picture() != null) {
            user.setProfile_picture(req.getProfile_picture());
        }
        return userRepository.save(user);
    }

    @Override
    public User findUserProfile(String jwt) {

        String email = tokenProvider.getEmailFromToken(jwt);
        if (email == null) {
            throw new BadCredentialsException("Received Invalid Token");
        }

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new UserException("User not found with email " + email);
        }
        return user;
    }

    @Override
    public List<User> searchUser(String query) {
        List<User> users = userRepository.searchUser(query);
        return users;
    }
}
