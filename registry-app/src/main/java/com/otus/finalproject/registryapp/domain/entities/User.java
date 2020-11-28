package com.otus.finalproject.registryapp.domain.entities;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Data
@Entity(name = "user")
@Table(name = "user", schema = "public")
@Accessors(chain = true)
public class User {
    @Id
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
}
