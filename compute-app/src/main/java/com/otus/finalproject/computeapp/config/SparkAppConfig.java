package com.otus.finalproject.computeapp.config;

import com.otus.finalproject.computeapp.properties.CassandraAppProperties;
import com.otus.finalproject.computeapp.properties.SparkAppProperties;
import lombok.RequiredArgsConstructor;
import org.apache.spark.SparkConf;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SparkAppConfig {

    @Qualifier("sparkAppProperties")
    private final SparkAppProperties sparkProperties;
    private final CassandraAppProperties cassandraProperties;

    /**
     * Бин конфигурации спарка
     */
    @Bean
    public SparkConf sparkConf() {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName(sparkProperties.getAppName());
        sparkConf.set("spark.cassandra.connection.host", cassandraProperties.getConnectionHost());
        sparkConf.set("spark.cassandra.connection.port", cassandraProperties.getPort());
        sparkConf.setMaster(sparkProperties.getMaster());
        sparkConf.set("spark.executor.memory", sparkProperties.getExecutorMemory());
        sparkConf.set("spark.driver.memory", sparkProperties.getDriverMemory());
        sparkConf.set("spark.driver.maxResultSize", sparkProperties.getDriverMaxResultSize());
        return sparkConf;
    }
}
