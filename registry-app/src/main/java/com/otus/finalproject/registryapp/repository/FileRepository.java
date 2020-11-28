package com.otus.finalproject.registryapp.repository;

import com.otus.finalproject.registryapp.domain.entities.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileRepository extends JpaRepository<File, String> {
}
