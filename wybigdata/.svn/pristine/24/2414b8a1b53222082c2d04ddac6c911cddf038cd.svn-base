package jdbc;

import common.utils.jdbc.ConnectionPool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;


import java.util.Arrays;
import java.util.Iterator;

public class DBUtilsTest {
    public static void main(String[] args) throws InterruptedException {
        SparkConf conf = new SparkConf().setAppName("V2.0").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(2));
        jssc.sparkContext().setLogLevel("WARN");
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
                    QueryRunner qr = new QueryRunner(ConnectionPool.getDataSource());

                    String sql1 = "select MDGEN_ENT0002('wy12345555') AS QUOTASVALUE from dual";
                    Object exec_sql1 = qr.query(sql1, new ScalarHandler<Object>("QUOTASVALUE"));
                    System.out.println("exec_sql:"+exec_sql1);

                    /*执行sql程序
                    String sql = "select EXEC_SQL from ide_quotas WHERE QUOTAS_CODE='MDGEN_ENT0002' ;";
                    Object exec_sql = qr.query(sql1, new ScalarHandler<Object>("EXEC_SQL"));
                    System.out.println("exec_sql:"+exec_sql);
                    */
                    /*向mysql插入时间
                    Date date = new Date();//获得系统时间.
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowTime = sdf.format(date);//将时间格式转换成符合Timestamp要求的格式.
                    Timestamp dates = Timestamp.valueOf(nowTime);//把时间转换
                    String insertSql = "insert into std_ent_alter_list(UUID,REQ_ID,BUSINESSID,CREATETIME) values(?,?,?,?)";
                    int update = qr.update(insertSql, tuple2._1, tuple2._2, 1,dates);
                    System.out.println(update+":dates");
                    */
                }
            });
        });
        jssc.start();
        jssc.awaitTermination();
    }
}
