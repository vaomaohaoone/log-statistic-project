package com.otus.finalproject.registryapp.service;

import com.otus.finalproject.registryapp.role.FileRoles;
import com.otus.finalproject.registryapp.domain.entities.File;
import com.otus.finalproject.registryapp.domain.entities.FilePermission;
import com.otus.finalproject.registryapp.domain.entities.User;
import com.otus.finalproject.registryapp.domain.entities.id.FilePermissionId;
import com.otus.finalproject.registryapp.repository.FilePermissionRepository;
import com.otus.finalproject.registryapp.repository.FileRepository;
import com.otus.finalproject.registryapp.repository.UserRepository;
import com.otus.finalproject.registryapp.service.utils.UserUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FileService {
    private final FileRepository fileRepository;
    private final FilePermissionRepository filePermissionRepository;
    private final UserRepository userRepository;
    private final UserUtils userUtils;

    @Transactional
    public File createFileEntity(File file, String userName) {
        file = fileRepository.save(file);
        User user = userRepository.findById(userName).orElseThrow(() -> new RuntimeException("No such user"));
        FilePermission filePermission = new FilePermission()
                .setFilePermissionId(
                        new FilePermissionId()
                        .setFilePath(file.getPath())
                        .setUserLogin(user.getLogin())
                )
                .setRole(FileRoles.CREATOR)
                .setFile(file)
                .setUser(user);
        filePermissionRepository.save(filePermission);
        return file;
    }

    @Transactional
    public void deleteFileEntityWithPermissions(File file, String userName) {
        User user = userRepository.findById(userName).orElseThrow(() -> new RuntimeException("No such user"));
        Optional<FilePermission> optionalFilePermission = filePermissionRepository.findByFileAndUser(file, user);
        if (optionalFilePermission.isPresent() && optionalFilePermission.get().getRole().equals(FileRoles.CREATOR)) {
            filePermissionRepository.deleteAllByFile_Path(file.getPath());
            fileRepository.deleteById(file.getPath());
        }
    }

    public File getFileByPathOrThrow(String path) {
        return fileRepository.findById(path).orElseThrow(() ->
                new NoSuchElementException(String.format("File with path '%s' does not exist", path))
        );
    }

    public List<File> getAllFiles() {
        if (userUtils.isAdminUser())
            return fileRepository.findAll();
        else {
            String userName = userUtils.getUserName();
            return filePermissionRepository.findByUser_Login(userName).stream().map(FilePermission::getFile).collect(Collectors.toList());
        }
    }
}
