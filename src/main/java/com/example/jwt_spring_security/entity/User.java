package com.example.jwt_spring_security.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "user")
@Data // lombok
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    private String password;
}
