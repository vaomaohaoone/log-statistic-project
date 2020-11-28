package com.otus.finalproject.kafkasender.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.finalproject.common.LogData;
import com.otus.finalproject.kafkasender.properties.KafkaSenderProperties;
import com.otus.finalproject.kafkasender.service.LogFileTail;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class KafkaSenderController {

    private final TaskExecutor taskExecutor;
    private final KafkaTemplate<Long, LogData> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final KafkaSenderProperties kafkaSenderProperties;
    private final List<String> registeredFiles = new ArrayList<>();

    @PostMapping("/stream")
    public HttpStatus registerFile(@RequestParam("path") String path) {
        File fileObject = new File(path);
        if (fileObject.isFile()) {
            if (!registeredFiles.contains(path)) {
                taskExecutor.execute(new LogFileTail(fileObject, kafkaTemplate, objectMapper, kafkaSenderProperties));
                registeredFiles.add(path);
                return HttpStatus.CREATED;
            }else
                return HttpStatus.ALREADY_REPORTED;
        }
        else
            return HttpStatus.NO_CONTENT;
    }
}
