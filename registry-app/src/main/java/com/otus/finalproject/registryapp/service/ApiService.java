package com.otus.finalproject.registryapp.service;

import com.otus.finalproject.common.ComputeData;
import com.otus.finalproject.common.ResultData;
import com.otus.finalproject.registryapp.domain.entities.File;
import com.otus.finalproject.registryapp.repository.FileRepository;
import com.otus.finalproject.registryapp.service.client.ComputeClient;
import com.otus.finalproject.registryapp.service.client.SenderClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApiService {
    private final ComputeClient computeClient;
    private final SenderClient senderClient;
    private final FileRepository fileRepository;

    @PreAuthorize("@filePermissionService.hasPermission(#computeData.filePath, authentication)")
    public List<ResultData> compute(ComputeData computeData) {
        return computeClient.compute(computeData);
    }

    @PreAuthorize("@filePermissionService.hasPermission(#filePath, authentication)")
    public HttpStatus streamFile(String filePath) {
        File file = fileRepository.findById(filePath).get();
        if (!file.getIsRegistered()) {
            HttpStatus httpStatus = senderClient.streamFile(filePath);
            if (httpStatus.equals(HttpStatus.CREATED)) {
                fileRepository.save(fileRepository.findById(filePath).get().setIsRegistered(true));
            }
            return httpStatus;
        }
        else
            return HttpStatus.ALREADY_REPORTED;
    }
}
