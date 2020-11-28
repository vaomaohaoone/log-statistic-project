package com.otus.finalproject.kafkasender.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties приложения для работы с кафкой
 * */
@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaSenderProperties {
    private String topic;
    private String producerId;
    private String bootstrapServers;
    private Boolean startAtBeginning;
    private Integer sampleInterval;
}
