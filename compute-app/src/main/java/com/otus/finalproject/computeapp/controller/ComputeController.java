package com.otus.finalproject.computeapp.controller;

import com.otus.finalproject.common.ComputeData;
import com.otus.finalproject.common.ResultData;
import com.otus.finalproject.computeapp.service.ComputeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/compute")
@RequiredArgsConstructor
public class ComputeController {

    private final ComputeService computeService;

    @PostMapping
    public ResponseEntity<List<ResultData>> compute(@RequestBody ComputeData computeData) {
        return ResponseEntity.ok(computeService.compute(computeData.getSeconds(), computeData.getFilePath()));
    }

}
