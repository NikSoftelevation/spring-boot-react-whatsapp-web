package com.springbootwhatspp.controller;

import com.springbootwhatspp.model.User;
import com.springbootwhatspp.response.ApiResponse;
import com.springbootwhatspp.request.UpdateUserRequest;
import com.springbootwhatspp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(userService.findUserProfile(token), HttpStatus.ACCEPTED);
    }

    @GetMapping("/{query}")
    public ResponseEntity<List<User>> searchUserHandler(@PathVariable("query") String query) {
        return new ResponseEntity<>(userService.searchUser(query), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponse> updateUserHandler(@RequestBody UpdateUserRequest updateUserRequest, @RequestHeader("Authorization") String token) {

        User user = userService.findUserProfile(token);

        User updatedUser = userService.updateUser(user.getId(), updateUserRequest);

        return new ResponseEntity<>(new ApiResponse("user updated successfully", true), HttpStatus.ACCEPTED);
    }
}
