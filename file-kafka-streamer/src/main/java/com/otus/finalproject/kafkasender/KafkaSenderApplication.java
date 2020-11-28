package com.otus.finalproject.kafkasender;

import com.otus.finalproject.kafkasender.properties.KafkaSenderProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(value = KafkaSenderProperties.class)
public class KafkaSenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaSenderApplication.class, args);
	}

}
