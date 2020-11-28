package com.otus.finalproject.registryapp.service;

import com.otus.finalproject.registryapp.domain.entities.User;
import com.otus.finalproject.registryapp.repository.UserRepository;
import com.otus.finalproject.registryapp.role.UserRoles;
import com.otus.finalproject.registryapp.service.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@PreAuthorize("hasRole('" + UserRoles.ADMIN_ROLE + "')")
public class UserService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserByLogin(String login) {
        userRepository.deleteById(login);
    }

    @PreAuthorize("hasRole('" + UserRoles.ADMIN_ROLE + "') or hasRole('" + UserRoles.USER_ROLE + "')")
    public Optional<User> getUserByLogin(String login) {
        if (userUtils.isAdminUser() || userUtils.getUserName().equals(login))
            return userRepository.findById(login);
        else
            return Optional.empty();
    }

    @PreAuthorize("hasRole('" + UserRoles.ADMIN_ROLE + "') or hasRole('" + UserRoles.USER_ROLE + "')")
    User getUserByLoginOrThrow(String login) {
        return userRepository.findById(login).orElseThrow(() ->
                new NoSuchElementException(String.format("User with login '%s' does not exist", login))
        );
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
