package com.springbootwhatspp.service.impl;

import com.springbootwhatspp.exception.ChatException;
import com.springbootwhatspp.exception.MessageException;
import com.springbootwhatspp.exception.UserException;
import com.springbootwhatspp.model.Chat;
import com.springbootwhatspp.model.Message;
import com.springbootwhatspp.model.User;
import com.springbootwhatspp.repository.MessageRepository;
import com.springbootwhatspp.request.SendMessageRequest;
import com.springbootwhatspp.service.ChatService;
import com.springbootwhatspp.service.MessageService;
import com.springbootwhatspp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepository messageRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;

    @Override
    public Message sendMessage(SendMessageRequest sendMessageRequest) throws UserException, ChatException {

        User user = userService.findUserByUserId(sendMessageRequest.getUserId());

        Chat chat = chatService.findByChatId(sendMessageRequest.getChatId());

        Message message = new Message();

        message.setUser(user);
        message.setChat(chat);
        message.setContent(sendMessageRequest.getContent());
        message.setTimeStamp(LocalDateTime.now());

        return message;
    }

    @Override
    public List<Message> findChatMessages(int chatId, User reqUser) throws ChatException, UserException {

        Chat chat = chatService.findByChatId(chatId);

        if (!chat.getUsers().contains(reqUser)) {
            throw new UserException("You atr not related to this chat " + chatId);
        }

        List<Message> messages = messageRepository.findByChatId(chatId);

        return messages;
    }

    @Override
    public Message findByMessageId(int messageId) throws MessageException {
        return messageRepository.findById(messageId).orElseThrow(() -> new MessageException("Message not found with messageId" + messageId));
    }

    @Override
    public void deleteMessage(int messageId, User reqUser) throws MessageException, UserException {

        Message message = findByMessageId(messageId);

        if (message.getUser().getId() == reqUser.getId()) {
            messageRepository.deleteById(messageId);
        }
        throw new UserException("You can't deleted another user's message" + reqUser.getFull_name());
    }
}
