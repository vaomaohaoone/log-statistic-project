package com.otus.finalproject.registryapp.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(example = "admin")
    private String login;
    @Schema(example = "admin")
    @Column(name = "password")
    private String password;
    @Schema(example = "ADMIN")
    @Column(name = "role")
    private String role;
}
