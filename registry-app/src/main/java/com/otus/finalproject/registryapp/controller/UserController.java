package com.otus.finalproject.registryapp.controller;

import com.otus.finalproject.registryapp.domain.entities.User;
import com.otus.finalproject.registryapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("/{login}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByLogin(@PathVariable("login") String login) {
        userService.deleteUserByLogin(login);
    }

    @GetMapping("/{login}")
    public ResponseEntity<User> getByLogin(@PathVariable("login") String login) {
        return ResponseEntity.of(userService.getUserByLogin(login));
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
