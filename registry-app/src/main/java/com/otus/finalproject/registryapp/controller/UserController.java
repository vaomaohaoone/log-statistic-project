package com.otus.finalproject.registryapp.controller;

import com.otus.finalproject.registryapp.domain.entities.User;
import com.otus.finalproject.registryapp.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Tag(name = "user-controller", description = "Контроллер для управления пользователями приложения")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(description = "Создание пользователя")
    public ResponseEntity<User> createUser(
            @Parameter(description = "user-body", required = true, name = "user") @RequestBody User user) {
        return ResponseEntity.ok(userService.saveUser(user));
    }

    @DeleteMapping("/{login}")
    @Operation(description = "Удаление пользователя по login")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUserByLogin(
            @Parameter(description = "Логин пользователя", required = true, name = "login", example = "admin") @PathVariable("login") String login
    ) {
        userService.deleteUserByLogin(login);
    }

    @GetMapping("/{login}")
    @Operation(description = "Получение пользователя по login")
    public ResponseEntity<User> getByLogin(
            @Parameter(description = "Логин пользователя", required = true, name = "login", example = "admin") @PathVariable("login") String login
    ) {
        return ResponseEntity.of(userService.getUserByLogin(login));
    }

    @GetMapping
    @Operation(description = "Получение списка всех пользователей")
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

}
