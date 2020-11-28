package com.otus.finalproject.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultData implements Serializable {
    private Timestamp from;
    private Timestamp to;
    private int level;
    private long count;
}
