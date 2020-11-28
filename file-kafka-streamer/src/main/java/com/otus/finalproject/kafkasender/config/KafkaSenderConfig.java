package com.otus.finalproject.kafkasender.config;

import com.otus.finalproject.common.LogData;
import com.otus.finalproject.kafkasender.properties.KafkaSenderProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.LongSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaSenderConfig {

    private final KafkaSenderProperties kafkaSenderProperties;

    @Bean
    public KafkaAdmin admin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaSenderProperties.getBootstrapServers());
        return new KafkaAdmin(configs);
    }

    /**
     * Бин используемого кафка топика, если топик не существует, создаётся новый
     */
    @Bean
    public NewTopic topicApp() {
        return TopicBuilder.name(kafkaSenderProperties.getTopic())
                .build();
    }

    @Bean
    public ProducerFactory<Long, LogData> producerFactory() {
        return new DefaultKafkaProducerFactory<>(producerConfigs());
    }

    @Bean
    public KafkaTemplate<Long, LogData> kafkaTemplate() {
        return new KafkaTemplate<Long, LogData>(producerFactory());
    }

    /**
     * Метод выдачи конифгурации кафка-продьюсера
     */
    private Map<String, Object> producerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaSenderProperties.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, kafkaSenderProperties.getProducerId());
        return props;
    }
}
