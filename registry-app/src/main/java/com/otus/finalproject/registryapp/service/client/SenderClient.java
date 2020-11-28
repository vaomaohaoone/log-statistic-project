package com.otus.finalproject.registryapp.service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("file-kafka-streamer")
public interface SenderClient {

    @PostMapping("/stream")
    HttpStatus streamFile(@RequestParam("path") String path);
}
