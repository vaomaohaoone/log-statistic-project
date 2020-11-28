package com.otus.finalproject.kafkasender.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.finalproject.common.LogData;
import com.otus.finalproject.kafkasender.properties.KafkaSenderProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;

import java.io.*;

@RequiredArgsConstructor
@Slf4j
public class LogFileTail implements Runnable {


    private final File logfile;

    private final KafkaTemplate<Long, LogData> kafkaTemplate;

    private final ObjectMapper objectMapper;

    private final KafkaSenderProperties kafkaSenderProperties;

    protected void fireNewLogFileLine(String line) {
        try {
            LogData logData = objectMapper.readValue(line, LogData.class);
            logData.setId(logData.getTimestamp().getTime());
            logData.setPath(logfile.getAbsolutePath());
            log.info("<= sending LogData {}", writeValueAsString(logData));
            kafkaTemplate.send(kafkaSenderProperties.getTopic(), logData);
        } catch (JsonProcessingException jsonMappingException) {
            jsonMappingException.printStackTrace();
        }
    }

    @Override
    public void run() {
        long filePointer;
        if (kafkaSenderProperties.getStartAtBeginning()) {
            filePointer = 0;
        } else {
            filePointer = this.logfile.length();
        }

        // Start tailing
        while (true) {
            try {
                RandomAccessFile file = new RandomAccessFile(logfile, "r");
                long fileLength = this.logfile.length();
                if (fileLength < filePointer) {
                    filePointer = 0;
                }
                if (fileLength > filePointer) {
                    // There is data to read
                    file.seek(filePointer);
                    String line;
                    while ((line = file.readLine()) != null) {
                        this.fireNewLogFileLine(line);
                    }
                    filePointer = file.getFilePointer();
                }
                // Sleep for the specified interval
                Thread.sleep(kafkaSenderProperties.getSampleInterval());
            } catch (InterruptedException | IOException ex) {
                ex.printStackTrace();
                break;
            }
        }

    }

    /**
     * Метод преобразования записи о гражданине из объекта в String
     *
     * @param row - запись о гражданине
     * @return String запись
     */
    private String writeValueAsString(Object row) {
        try {
            return objectMapper.writeValueAsString(row);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}