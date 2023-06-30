package com.springbootwhatspp.service;

import com.springbootwhatspp.exception.ChatException;
import com.springbootwhatspp.exception.UserException;
import com.springbootwhatspp.model.Chat;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.request.GroupChatRequest;

import java.util.List;

public interface ChatService {
    public Chat createChat(User reqUser, int userId) throws UserException;

    public Chat findByChatId(int chatId) throws ChatException;

    public List<Chat> findAllChatsByUserId(int userId) throws UserException;

    public Chat createGroup(GroupChatRequest request, User reqUser) throws UserException;

    public Chat addUserToGroup(int userId, int chatId, User reqUser) throws UserException, ChatException;

    public Chat renameGroup(int chatId, String groupName, User reqUser) throws UserException, ChatException;

    public Chat removeFromGroup(int chatId, int userId, User reqUser) throws UserException, ChatException;

    public void deleteChat(int chatId, int reqUserId) throws ChatException, UserException;
}