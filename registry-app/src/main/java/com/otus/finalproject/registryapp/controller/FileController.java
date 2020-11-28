package com.otus.finalproject.registryapp.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.otus.finalproject.common.ComputeData;
import com.otus.finalproject.common.ResultData;
import com.otus.finalproject.registryapp.domain.entities.File;
import com.otus.finalproject.registryapp.service.ApiService;
import com.otus.finalproject.registryapp.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.otus.finalproject.registryapp.util.CheckerThrowable.checkThrowable;

@RestController
@RequiredArgsConstructor
@RequestMapping("/file")
@Slf4j
public class FileController {
    private final FileService fileService;
    private final ApiService apiService;

    @PostMapping
    @HystrixCommand(fallbackMethod = "handleSaveFile")
    public ResponseEntity<File> saveFile(@RequestBody File file, Principal principal) {
        return ResponseEntity.ok(fileService.createFileEntity(file, principal.getName()));
    }

    @PostMapping("/compute")
    @HystrixCommand(fallbackMethod = "handleGetStatistic")
    public ResponseEntity<List<ResultData>> getStatistic(@RequestBody ComputeData computeData) {
        return ResponseEntity.ok(apiService.compute(computeData));
    }

    @PostMapping("/stream")
    @HystrixCommand(fallbackMethod = "handleStreamFile")
    public HttpStatus streamFile(@RequestParam("path") String path) {
        return apiService.streamFile(path);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @HystrixCommand(fallbackMethod = "handleDeleteFile")
    public void deleteFile(@RequestBody File file, Principal principal) {
        fileService.deleteFileEntityWithPermissions(file, principal.getName());
    }

    @GetMapping
    @HystrixCommand(fallbackMethod = "handleGetFiles")
    public ResponseEntity<List<File>> getFiles() {
        return ResponseEntity.ok(fileService.getAllFiles());
    }

    ResponseEntity<File> handleSaveFile(Throwable throwable) {
        checkThrowable(throwable);
        return ResponseEntity.of(Optional.empty());
    }

    ResponseEntity<List<ResultData>> handleGetStatistic(Throwable throwable) {
        checkThrowable(throwable);
        return ResponseEntity.ok(Collections.emptyList());
    }

    void handleDeleteFile(Throwable throwable) {
        checkThrowable(throwable);
    }

    HttpStatus handleStreamFile(Throwable throwable) {
        checkThrowable(throwable);
        return HttpStatus.I_AM_A_TEAPOT;
    }

    ResponseEntity<List<File>> handleGetFiles(Throwable throwable) {
        checkThrowable(throwable);
        return ResponseEntity.ok(Collections.emptyList());
    }

}
