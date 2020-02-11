package jdbc;

import common.utils.jdbc.ConnectionPool;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.*;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;

import java.sql.*;
import java.util.Arrays;
import java.util.Iterator;

public class JDBCtest {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("V2.0").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(2));

        final JavaReceiverInputDStream<String> node01 = jssc.socketTextStream("node01", 9999);
        final JavaDStream<String> stringJavaDStream = node01.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                final String[] split = s.split(" ");
                return Arrays.asList(split).iterator();
            }
        });
        final JavaPairDStream<String, Integer> stringIntegerJavaPairDStream = stringJavaDStream.mapToPair(new PairFunction<String, String, Integer>() {
            @Override
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2(s, 1);
            }
        });
        final JavaPairDStream<String, Integer> stringIntegerJavaPairDStream1 = stringIntegerJavaPairDStream.reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer v1, Integer v2) throws Exception {

                return v1 + v2;
            }
        });

        stringIntegerJavaPairDStream1.print();
        stringIntegerJavaPairDStream1.foreachRDD(rdd -> {
            rdd.foreach(new VoidFunction<Tuple2<String, Integer>>() {
                @Override
                public void call(Tuple2<String, Integer> tuple2) throws Exception {
                    final Connection connection = ConnectionPool.getConnection();
                    String sql = "insert into test.b(id,username, scores) values(?,?,?)";
                    String sql1 = "select * from b";
                    final PreparedStatement preparedStatement = connection.prepareStatement(sql);
                    final PreparedStatement ps = connection.prepareStatement(sql1);
                    preparedStatement.setInt(1, 2);
                    preparedStatement.setString(2, tuple2._1);
                    preparedStatement.setInt(3, tuple2._2);
                    preparedStatement.executeUpdate();

                    //执行更新操作
                    final ResultSet resultSet = ps.executeQuery();

                    resultSet.last(); // 将光标移到结果数据集的最后一行，用来下面查询共有多少行记录
                    System.out.println("共有" + resultSet.getRow() + "行记录：");
                    resultSet.beforeFirst(); // 将光标移到结果数据集的开头
                    while (resultSet.next()) { // 循环读取结果数据集中的所有记录
                        System.out.println(resultSet.getRow() + "、 学号:" + resultSet.getString("id")
                                + "\t姓名:" + resultSet.getString("username") + "\t年龄:"
                                + resultSet.getInt("scores"));
                    }

                    ConnectionPool.closeCon(preparedStatement, connection);
                }
            });
        });
        jssc.start();
        jssc.awaitTermination();
    }
}
