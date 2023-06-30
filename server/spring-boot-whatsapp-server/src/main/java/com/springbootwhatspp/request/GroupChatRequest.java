package com.springbootwhatspp.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GroupChatRequest {
    private List<Integer> userIds;
    private String chat_name;
    private String chat_image;
}