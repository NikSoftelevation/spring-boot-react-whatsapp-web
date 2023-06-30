package com.springbootwhatspp.service;

import com.springbootwhatspp.exception.ChatException;
import com.springbootwhatspp.exception.MessageException;
import com.springbootwhatspp.exception.UserException;
import com.springbootwhatspp.model.Message;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.request.SendMessageRequest;

import java.util.List;

public interface MessageService {

    public Message sendMessage(SendMessageRequest sendMessageRequest) throws UserException, ChatException;

    public List<Message> findChatMessages(int chatId, User reqUser) throws ChatException, UserException;

    public Message findByMessageId(int messageId) throws MessageException;

    public void deleteMessage(int messageId, User reqUser) throws MessageException, UserException;
}