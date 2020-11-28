package com.otus.finalproject.registryapp.domain.data;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FilePermissionBody {
    @Schema(example = "/home/logs/app.log")
    private String path;
    @Schema(example = "admin")
    private String login;
}
