package com.otus.finalproject.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LogData implements Serializable {
    private Long id;
    private Timestamp timestamp;
    private String host;
    private String process;
    private Integer level;
    private String path;

    public static String getTimestampFieldName() {
        for (Field field: LogData.class.getDeclaredFields()) {
            if (field.getType().equals(Timestamp.class))
                return field.getName();
        }
        throw new RuntimeException("No field with Timestamp data type in LogData!!!");
    }

    public static String getLevelFieldName() {
        for (Field field: LogData.class.getDeclaredFields()) {
            if (field.getName().contains("level"))
                return field.getName();
        }
        throw new RuntimeException("No field with \"level\" word in LogData!!!");
    }
}
