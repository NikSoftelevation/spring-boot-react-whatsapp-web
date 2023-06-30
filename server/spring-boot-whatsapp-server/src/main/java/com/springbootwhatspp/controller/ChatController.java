package com.springbootwhatspp.controller;

import com.springbootwhatspp.exception.ChatException;
import com.springbootwhatspp.exception.UserException;
import com.springbootwhatspp.model.Chat;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.request.GroupChatRequest;
import com.springbootwhatspp.request.SingleChatRequest;
import com.springbootwhatspp.response.ApiResponse;
import com.springbootwhatspp.service.ChatService;
import com.springbootwhatspp.service.UserService;
import lombok.Getter;
import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chats")
public class ChatController {
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;

    @PostMapping("/single")
    public ResponseEntity<Chat> createChat(@RequestBody SingleChatRequest singleChatRequest, @RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserProfile(jwt);

        return new ResponseEntity<>(chatService.createChat(reqUser, singleChatRequest.getUserId()), HttpStatus.CREATED);
    }

    @PostMapping("/group")
    public ResponseEntity<Chat> createGroup(@RequestBody GroupChatRequest groupChatRequest, @RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserProfile(jwt);

        return new ResponseEntity<>(chatService.createGroup(groupChatRequest, reqUser), HttpStatus.CREATED);
    }

    @GetMapping("/{chatId}")
    public ResponseEntity<Chat> findChatByChatId(@PathVariable("chatId") int chatId, @RequestHeader("Authorization") String jwt) {

        return new ResponseEntity<>(chatService.findByChatId(chatId), HttpStatus.OK);
    }

    @GetMapping("/user")
    public ResponseEntity<List<Chat>> findAllChatsByUserId(@RequestHeader("Authorization") String jwt) {

        User requser = userService.findUserProfile(jwt);

        List<Chat> allChatsByUserId = chatService.findAllChatsByUserId(requser.getId());

        return new ResponseEntity<>(allChatsByUserId, HttpStatus.OK);
    }

    @PutMapping("/{chatId}/add/{userId}")
    public ResponseEntity<Chat> adduserToGroup(@PathVariable("userId") int userId, @PathVariable("chatId") int chatId, @RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserProfile(jwt);

        Chat addedUserToGroup = chatService.addUserToGroup(userId, chatId, reqUser);

        return new ResponseEntity<>(addedUserToGroup, HttpStatus.ACCEPTED);
    }

    @PutMapping("/{chatId}/remove/{userId}")
    public ResponseEntity<Chat> removeUserFromGroup(@PathVariable("userId") int userId, @PathVariable("chatId") int chatId, @RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserProfile(jwt);

        Chat removeFromGroup = chatService.removeFromGroup(chatId, userId, reqUser);

        return new ResponseEntity<>(removeFromGroup, HttpStatus.GONE);
    }

    @PutMapping("/{chatId}/groupName")
    public ResponseEntity<Chat> renameGroup(@PathVariable("chatId") int chatId, @RequestParam("groupName") String groupName, @RequestHeader("Authorization") String jwt) {

        try {
            User reqUser = userService.findUserProfile(jwt);

            Chat renameGroup = chatService.renameGroup(chatId, groupName, reqUser);

            return new ResponseEntity<>(renameGroup, HttpStatus.ACCEPTED);

        } catch (UserException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);
        } catch (ChatException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @DeleteMapping("/delete/{chatId}")
    public ResponseEntity<ApiResponse> deleteChat(@PathVariable("chatId") int chatId, @RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserProfile(jwt);

        chatService.deleteChat(chatId, reqUser.getId());

        return new ResponseEntity<>(new ApiResponse("Chat with chatId " + chatId + "deleted successfully", true), HttpStatus.GONE);
    }
}