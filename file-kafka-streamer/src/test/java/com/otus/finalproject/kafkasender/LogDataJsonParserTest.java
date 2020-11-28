package com.otus.finalproject.kafkasender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.finalproject.common.LogData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class LogDataJsonParserTest {

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() throws JsonProcessingException {
		String line = "{\"timestamp\":\"2020-11-20T00:00:00\",\"hostName\":\"localhost\",\"process\":\"root\",\"level\":0}";
		LogData logData = objectMapper.readValue(line, LogData.class);
		Assertions.assertEquals("localhost", logData.getHost());
	}

}
