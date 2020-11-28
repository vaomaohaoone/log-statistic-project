package com.otus.finalproject.registryapp.repository;

import com.otus.finalproject.registryapp.domain.entities.File;
import com.otus.finalproject.registryapp.domain.entities.FilePermission;
import com.otus.finalproject.registryapp.domain.entities.User;
import com.otus.finalproject.registryapp.domain.entities.id.FilePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FilePermissionRepository extends JpaRepository<FilePermission, FilePermissionId> {
    Optional<FilePermission> findByFile_PathAndUser(String filePath, User user);
    List<FilePermission> findByUser_Login(String login);
    Optional<FilePermission> findByUser_LoginAndFile_Path(String login, String path);
    void deleteAllByFile_Path(String filePath);
}
