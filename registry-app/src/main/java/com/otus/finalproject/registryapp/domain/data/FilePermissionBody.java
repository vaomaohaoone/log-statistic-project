package com.otus.finalproject.registryapp.domain.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class FilePermissionBody {
    private String path;
    private String login;
}
