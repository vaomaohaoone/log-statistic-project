package com.otus.finalproject.registryapp.service.security;

import java.util.Optional;

import com.otus.finalproject.registryapp.domain.entities.User;
import com.otus.finalproject.registryapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findById(login);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return org.springframework.security.core.userdetails.User
                .withUsername(userOptional.get().getLogin())
                .password(userOptional.get().getPassword())
                .roles(userOptional.get().getRole())
                .build();
    }
}