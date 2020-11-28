package com.otus.finalproject.registryapp.repository;

import com.otus.finalproject.registryapp.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, String> {
}
