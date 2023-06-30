package com.springbootwhatspp.service.impl;

import com.springbootwhatspp.exception.ChatException;
import com.springbootwhatspp.exception.UserException;
import com.springbootwhatspp.model.Chat;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.request.GroupChatRequest;
import com.springbootwhatspp.repository.ChatRepository;
import com.springbootwhatspp.repository.UserRepository;
import com.springbootwhatspp.service.ChatService;
import com.springbootwhatspp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import java.util.List;
import java.util.Optional;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ChatRepository chatRepository;
    @Autowired
    private UserService userService;

    @Override
    public Chat createChat(User reqUser, int userId) throws UserException {

        User user = userService.findUserByUserId(userId);

        Chat isChatExists = chatRepository.findSingleChatByUserIds(user, reqUser);
        if (isChatExists != null) {
            return isChatExists;
        }
        Chat chat = new Chat();
        chat.setCreatedBy(reqUser);
        chat.getUsers().add(reqUser);
        chat.getUsers().add(user);
        chat.setGroup(false);

        return chat;
    }

    @Override
    public Chat findByChatId(int chatId) throws ChatException {
        return chatRepository.findById(chatId).orElseThrow(() -> new ChatException("Chat not found with chatId" + chatId));
    }

    @Override
    public List<Chat> findAllChatsByUserId(int userId) throws UserException {

        User user = userService.findUserByUserId(userId);

        List<Chat> chats = chatRepository.findChatByUserId(user.getId());

        return chats;
    }

    @Override
    public Chat createGroup(GroupChatRequest request, User reqUser) throws UserException {

        Chat group = new Chat();

        group.setGroup(true);
        group.setChat_image(request.getChat_image());
        group.setChat_name(request.getChat_name());
        group.setCreatedBy(reqUser);
        group.getAdmins().add(reqUser);

        for (int userId : request.getUserIds()) {
            User user = userService.findUserByUserId(userId);
            group.getUsers().add(user);
        }

        return group;
    }

    @Override
    public Chat addUserToGroup(int userId, int chatId, User reqUser) throws UserException, ChatException {

        Optional<Chat> findByChatId = chatRepository.findById(chatId);

        User user = userService.findUserByUserId(userId);
        if (findByChatId.isPresent()) {
            Chat chat = findByChatId.get();
            if (chat.getAdmins().contains(reqUser)) {
                chat.getUsers().add(user);
                return chatRepository.save(chat);
            } else {
                throw new UserException("You are not admin");
            }
        }
        throw new ChatException("Chat not found with chatId " + chatId);
    }

    @Override
    public Chat renameGroup(int chatId, String groupName, User reqUser) throws UserException, ChatException {

        Optional<Chat> chat = chatRepository.findById(chatId);

        if (chat.isPresent()) {
            Chat chatGet = chat.get();

            if (chatGet.getUsers().contains(reqUser)) {
                chatGet.setChat_name(groupName);
                return chatRepository.save(chatGet);
            }
            throw new UserException("You are member of this group");
        }
        throw new ChatException("No Chat found with chatId " + chatId);
    }

    @Override
    public Chat removeFromGroup(int chatId, int userId, User reqUser) throws UserException, ChatException {

        Optional<Chat> opt = chatRepository.findById(chatId);

        /*User user = userService.findUserByUserId(userId);

        if (opt.isPresent()) {
            Chat chat = opt.get();
            if (chat.getAdmins().contains(reqUser)) {
                chat.getUsers().remove(user);
                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(reqUser)) {
                if (user.getId().equals(reqUser.getId())) {
                    chat.getUsers().remove(user);
                    return chatRepository.save(chat);
                }
            } else {
                throw new UserException("You are not admin");
            }
        }
        throw new ChatException("Chat not found with chatId " + chatId);*/

        if (opt.isPresent()) {
            Chat chat = opt.get();

            // Check if the requesting user is an admin of the chat
            if (chat.getAdmins().contains(reqUser)) {
                User user = userService.findUserByUserId(userId);

                // Remove the user from the chat's list of users
                chat.getUsers().remove(user);

                // Save the updated chat and return it
                return chatRepository.save(chat);
            } else if (chat.getUsers().contains(reqUser)) {
                // Check if the requesting user is removing themselves from the chat
                if (reqUser.getId() == userId) {
                    // Remove the user from the chat's list of users
                    chat.getUsers().removeIf(u -> u.getId() == userId);

                    // Save the updated chat and return it
                    return chatRepository.save(chat);
                } else {
                    throw new UserException("You are not an admin of the chat");
                }
            } else {
                throw new UserException("You are not a member of the chat");
            }
        } else {
            throw new ChatException("Chat not found with chatId " + chatId);
        }
    }

    @Override
    public void deleteChat(int chatId, int reqUserId) throws ChatException, UserException {

        Optional<Chat> opt = chatRepository.findById(chatId);

        if (opt.isPresent()) {
            Chat chat = opt.get();

            if (!chat.getUsers().contains(reqUserId)) {
                throw new UserException("You are not a member of this chat");
            }

            chatRepository.deleteById(chat.getId());
        } else {
            throw new ChatException("No chat found with chatId " + chatId);
        }
    }
}