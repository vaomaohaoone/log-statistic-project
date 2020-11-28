package com.otus.finalproject.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComputeData {
    @Schema(example = "/home/logs/app.log", description = "Путь до файла")
    String filePath;
    @Schema(example = "300", description = "Интервал в секундах, на который нужно разбить отсечки времени")
    long seconds;
}
