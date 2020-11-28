package com.otus.finalproject.computeapp.service;

import com.datastax.spark.connector.japi.CassandraJavaUtil;
import com.otus.finalproject.common.LogData;
import com.otus.finalproject.common.ResultData;
import com.otus.finalproject.computeapp.properties.CassandraAppProperties;
import org.apache.spark.SparkConf;
import org.apache.spark.SparkContext;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.catalyst.expressions.GenericRowWithSchema;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.datastax.spark.connector.japi.CassandraJavaUtil.javaFunctions;

@Service
public class ComputeService {
    private final JavaSparkContext javaSparkContext;
    private final CassandraAppProperties cassandraProperties;
    private final SQLContext sqlContext;

    public ComputeService(SparkConf sparkConf, CassandraAppProperties cassandraProperties) {
        this.javaSparkContext = new JavaSparkContext(SparkContext.getOrCreate(sparkConf));
        this.sqlContext = new SQLContext(javaSparkContext);
        this.cassandraProperties = cassandraProperties;
    }

    /**
     * Метод подсчёта статистики с помощью spark RDD и spark SQL
     * @param seconds - интервал в секундах (шкала)
     * @param filePath - файл, по которому ведётся статистика
     * @return список выходных данных
     */
    public List<ResultData> compute(long seconds, String filePath) {

        JavaRDD<LogData> logDataJavaRDD = javaFunctions(javaSparkContext)
                .cassandraTable(
                        cassandraProperties.getKeySpace(),
                        cassandraProperties.getTable(),
                        CassandraJavaUtil.mapRowTo(LogData.class)
                )
                .filter(v1 -> v1.getPath().equals(filePath));
        String windowDuration = String.format("%d seconds", seconds);
        Column timestampColumn = new Column("timestamp");

        return sqlContext.createDataFrame(logDataJavaRDD, LogData.class)
                .as(Encoders.bean(LogData.class))
                .groupBy(org.apache.spark.sql.functions.window(timestampColumn, windowDuration), new Column("level"))
                .count()
                .orderBy("window", "count")
                .map((MapFunction<Row, ResultData>) v -> {
                    String[] timeDeltas = ((GenericRowWithSchema) v.getAs("window"))
                            .toString()
                            .replaceAll("\\[", "")
                            .replaceAll("\\]", "")
                            .split(",");
                    return new ResultData(
                            Timestamp.valueOf(timeDeltas[0]), Timestamp.valueOf(timeDeltas[1]),
                            v.getAs("level") , v.getAs("count")
                    );
                }, Encoders.bean(ResultData.class))
                .collectAsList();
    }

}
