package com.springbootwhatspp.model;

import com.sun.source.doctree.SeeTree;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String chat_name;
    private String chat_image;
    private boolean isGroup;

    @ManyToMany
    private Set<User> admins = new HashSet<>();

    @ManyToOne
    private User createdBy;

    @ManyToMany
    private Set<User> users = new HashSet<>();

    @OneToMany
    private List<Message> messages = new ArrayList<>();
}