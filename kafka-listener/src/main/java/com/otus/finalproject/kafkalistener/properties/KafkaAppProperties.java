package com.otus.finalproject.kafkalistener.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * Properties приложения для работы с кафкой
 * */
@ConfigurationProperties(prefix = "kafka")
@Data
public class KafkaAppProperties {
    private String topic;
    private String consumerGroupId;
    private String bootstrapServers;
}
