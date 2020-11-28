package com.otus.finalproject.registryapp.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.otus.finalproject.registryapp.domain.data.FilePermissionBody;
import com.otus.finalproject.registryapp.domain.entities.FilePermission;
import com.otus.finalproject.registryapp.service.FilePermissionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permission")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "file-permission-controller", description = "Контроллер управления доступа к файлам")
public class FilePermissionController {

    private final FilePermissionService filePermissionService;

    @GetMapping
    @Operation(description = "Получение списка доступов к файлам")
    @HystrixCommand(fallbackMethod = "handleGetAllowedFilePermissions")
    public ResponseEntity<List<FilePermission>> getAllowedFilePermissions() {
        return ResponseEntity.ok(filePermissionService.getAllowedFilePermissions());
    }

    @PostMapping
    @Operation(description = "Добавление разрешения на доступ к файлу")
    @HystrixCommand(fallbackMethod = "handleSetFilePermission")
    public ResponseEntity<FilePermission> setFilePermission(
            @Parameter(description = "Разрешение на доступ", required = true, name = "filePermissionBody") @RequestBody FilePermissionBody filePermissionBody
    ) {
        return ResponseEntity.ok(filePermissionService.saveFilePermission(filePermissionBody));
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Удаление разрешения на доступ к файлу")
    @HystrixCommand(fallbackMethod = "handleRemoveFilePermission")
    public void removeFilePermission(
            @Parameter(description = "Разрешение на доступ", required = true, name = "filePermissionBody") @RequestBody FilePermissionBody filePermissionBody
    ) {
        filePermissionService.removeFilePermission(filePermissionBody);
    }

    public ResponseEntity<List<FilePermission>> handleGetAllowedFilePermissions() {
        log.error("Cannot get any file permissions");
        return ResponseEntity.ok(Collections.emptyList());
    }

    public ResponseEntity<FilePermission> handleSetFilePermission(FilePermissionBody filePermissionBody) {
        log.error("Cannot set file permission with body: {}", filePermissionBody);
        return ResponseEntity.of(Optional.empty());
    }

    public void handleRemoveFilePermission(FilePermissionBody filePermissionBody) {
        log.error("Cannot remove file permission with body: {}", filePermissionBody);
    }
}
