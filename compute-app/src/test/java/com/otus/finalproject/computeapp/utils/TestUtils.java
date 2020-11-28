package com.otus.finalproject.computeapp.utils;


import com.otus.finalproject.common.ResultData;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

public class TestUtils {
    public final static String FILE_PATH = "/var/log/app.log";
    public static final Set<ResultData> expectedStatisticData = new HashSet<ResultData>() {{
        add(new ResultData(Timestamp.valueOf("2020-11-20 00:05:00"), Timestamp.valueOf("2020-11-20 00:10:00"), 0, 1));
        add(new ResultData(Timestamp.valueOf("2020-11-20 00:05:00"), Timestamp.valueOf("2020-11-20 00:10:00"), 1, 1));
        add(new ResultData(Timestamp.valueOf("2020-11-20 00:15:00"), Timestamp.valueOf("2020-11-20 00:20:00"), 1, 1));
        add(new ResultData(Timestamp.valueOf("2020-11-20 00:20:00"), Timestamp.valueOf("2020-11-20 00:25:00"), 1, 1));
        add(new ResultData(Timestamp.valueOf("2020-11-20 00:25:00"), Timestamp.valueOf("2020-11-20 00:30:00"), 2, 1));
        add(new ResultData(Timestamp.valueOf("2020-11-20 00:30:00"), Timestamp.valueOf("2020-11-20 00:35:00"), 3, 2));
        add(new ResultData(Timestamp.valueOf("2020-11-20 00:45:00"), Timestamp.valueOf("2020-11-20 00:50:00"), 3, 1));
        add(new ResultData(Timestamp.valueOf("2020-11-20 00:55:00"), Timestamp.valueOf("2020-11-20 01:00:00"), 4, 1));
    }};
}
