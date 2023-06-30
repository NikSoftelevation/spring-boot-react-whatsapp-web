package com.springbootwhatspp.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String full_name;
    private String email;
    private String profile_picture;
    private String password;

    // @ManyToMany(mappedBy = "user", cascade = CascadeType.ALL)
    //private List<Notification> notifications = new ArrayList<>();
}
