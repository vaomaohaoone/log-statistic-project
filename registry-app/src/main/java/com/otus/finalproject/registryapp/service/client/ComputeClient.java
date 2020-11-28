package com.otus.finalproject.registryapp.service.client;

import com.otus.finalproject.common.ComputeData;
import com.otus.finalproject.common.ResultData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient("compute")
public interface ComputeClient {
    @PostMapping("/compute")
    List<ResultData> compute(@RequestBody ComputeData computeData);
}
