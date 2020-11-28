package com.otus.finalproject.registryapp.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.otus.finalproject.common.ComputeData;
import com.otus.finalproject.common.ResultData;
import com.otus.finalproject.registryapp.domain.entities.File;
import com.otus.finalproject.registryapp.service.ApiService;
import com.otus.finalproject.registryapp.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Slf4j
@Tag(name = "file-controller", description = "Контроллер регистрации файлов в kafka, подсчёта статистики, и управления File сущностями")
public class FileController {
    private final FileService fileService;
    private final ApiService apiService;

    @PostMapping
    @Operation(description = "Регистрация файла в системе")
    @HystrixCommand(fallbackMethod = "handleSaveFile")
    public ResponseEntity<File> saveFile(
            @Parameter(description = "file-body", required = true, name = "file") @RequestBody File file, Principal principal) {
        return ResponseEntity.ok(fileService.createFileEntity(file, principal.getName()));
    }

    @PostMapping("/compute")
    @Operation(description = "Получение статистики по файлу (временной промежуток, уровень логгирования, количество)")
    @HystrixCommand(fallbackMethod = "handleGetStatistic")
    public ResponseEntity<List<ResultData>> getStatistic(
            @Parameter(description = "Входные параметры для подсчёта", required = true, name = "computeData") @RequestBody ComputeData computeData
    ) {
        return ResponseEntity.ok(apiService.compute(computeData));
    }

    @PostMapping("/stream")
    @Operation(description = "Эндпоинт начала стриминга файла в Kafka")
    @HystrixCommand(fallbackMethod = "handleStreamFile")
    public HttpStatus streamFile(
            @Parameter(description = "Путь до файла", required = true, name = "computeData", example = "/home/logs/app/log") @RequestParam("path") String path
    ) {
        return apiService.streamFile(path);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(description = "Эндпоинт удаления файла из бд")
    @HystrixCommand(fallbackMethod = "handleDeleteFile")
    public void deleteFile(
            @Parameter(description = "Путь до файла", required = true, name = "computeData", example = "/home/logs/app/log") @RequestParam("path") String path,
            Principal principal) {
        fileService.deleteFileEntityWithPermissions(path, principal.getName());
    }

    @GetMapping
    @Operation(description = "Получение списка всех разрешенных файлов")
    @HystrixCommand(fallbackMethod = "handleGetFiles")
    public ResponseEntity<List<File>> getFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    public ResponseEntity<File> handleSaveFile(File file, Principal principal) {
        log.error("Cannot save the file: {}", file);
        return ResponseEntity.of(Optional.empty());
    }

    public ResponseEntity<List<ResultData>> handleGetStatistic(ComputeData computeData) {
        log.error("Cannot get statistic for compute data: {}", computeData);
        return ResponseEntity.ok(Collections.emptyList());
    }

    public void handleDeleteFile(String path, Principal principal) {
        log.error("Cannot delete file with path: {}", path);
    }

    public HttpStatus handleStreamFile(String path) {
        log.error("Cannot stream file with path: {}", path);
        return HttpStatus.I_AM_A_TEAPOT;
    }

    public ResponseEntity<List<File>> handleGetFiles(Throwable throwable) {
        log.error("Cannot get any files");
        return ResponseEntity.ok(Collections.emptyList());
    }

}
