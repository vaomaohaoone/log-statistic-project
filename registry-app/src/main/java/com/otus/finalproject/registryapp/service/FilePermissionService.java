package com.otus.finalproject.registryapp.service;

import com.otus.finalproject.registryapp.domain.data.FilePermissionBody;
import com.otus.finalproject.registryapp.domain.entities.File;
import com.otus.finalproject.registryapp.domain.entities.FilePermission;
import com.otus.finalproject.registryapp.domain.entities.User;
import com.otus.finalproject.registryapp.domain.entities.id.FilePermissionId;
import com.otus.finalproject.registryapp.repository.FilePermissionRepository;
import com.otus.finalproject.registryapp.role.FileRoles;
import com.otus.finalproject.registryapp.service.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FilePermissionService {
    private final FilePermissionRepository filePermissionRepository;
    private final FileService fileService;
    private final UserService userService;
    private final UserUtils userUtils;

    public boolean hasPermission(String filePath, Authentication userName) {
        return filePermissionRepository.findByUser_Login(userName.getName()).stream()
                .anyMatch(filePermission -> filePermission.getFile().getPath().equals(filePath));
    }

    public List<FilePermission> getAllowedFilePermissions() {
        if (userUtils.isAdminUser())
            return filePermissionRepository.findAll();
        else
            return filePermissionRepository.findByUser_Login(userUtils.getUserName());
    }

    public FilePermission saveFilePermission(FilePermissionBody filePermission) {
        Optional<FilePermission> optionalFilePermission = filePermissionRepository
                .findByUser_LoginAndFile_Path(userUtils.getUserName(), filePermission.getPath());
        if (userUtils.isAdminUser() || (optionalFilePermission.isPresent() && optionalFilePermission.get().getRole().equals(FileRoles.CREATOR))) {
            File file = fileService.getFileByPathOrThrow(filePermission.getPath());
            User user = userService.getUserByLoginOrThrow(filePermission.getLogin());
            return filePermissionRepository.save(
                    new FilePermission()
                        .setFilePermissionId(
                            new FilePermissionId()
                                .setFilePath(filePermission.getPath())
                                .setUserLogin(filePermission.getLogin())
                        )
                        .setFile(file)
                        .setUser(user)
            );
        } else
            return null;
    }

    public void removeFilePermission(FilePermissionBody filePermission) {
        Optional<FilePermission> optionalFilePermission = filePermissionRepository
                .findByUser_LoginAndFile_Path(userUtils.getUserName(), filePermission.getPath());
        if (userUtils.isAdminUser() || (optionalFilePermission.isPresent() && optionalFilePermission.get().getRole().equals(FileRoles.CREATOR))) {
            filePermissionRepository.deleteById(
                    new FilePermissionId()
                    .setFilePath(filePermission.getPath())
                    .setUserLogin(filePermission.getLogin())
            );
        }
    }
}
