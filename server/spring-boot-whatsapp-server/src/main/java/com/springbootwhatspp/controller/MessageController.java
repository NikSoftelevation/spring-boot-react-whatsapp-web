package com.springbootwhatspp.controller;

import com.springbootwhatspp.model.Message;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.request.SendMessageRequest;
import com.springbootwhatspp.response.ApiResponse;
import com.springbootwhatspp.service.MessageService;
import com.springbootwhatspp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Message> sendMessage(@RequestBody SendMessageRequest sendMessageRequest, @RequestHeader("Authorization") String jwt) {

        User user = userService.findUserProfile(jwt);

        sendMessageRequest.setUserId(user.getId());

        return new ResponseEntity<>(messageService.sendMessage(sendMessageRequest), HttpStatus.CREATED);
    }

    @GetMapping("/chat/{chatId}")
    public ResponseEntity<List<Message>> getChatMessage(@PathVariable("chatId") int chatId, @RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserProfile(jwt);

        List<Message> messages = messageService.findChatMessages(chatId, reqUser);

        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    @GetMapping("/message/{messageId}")
    public ResponseEntity<Message> findByMessageId(@PathVariable("messageId") int messageId) {

        Message messageById = messageService.findByMessageId(messageId);

        return new ResponseEntity<>(messageById, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{messageId}")
    public ResponseEntity<ApiResponse> deleteMessage(@PathVariable("messageId") int messageId, @RequestHeader("Authorization") String jwt) {

        User reqUser = userService.findUserProfile(jwt);

        messageService.deleteMessage(messageId, reqUser);

        return new ResponseEntity<>(new ApiResponse("message with messageId" + messageId + "deleetd successfully", true), HttpStatus.GONE);
    }
}