package sparksql;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.pojo.r227.R227Data;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.List;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/11/25 13:02
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: vcloude01
 * @pachage_name: sparksql
 * @description:
 */
public class jsonsparksql3 {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf sparkConf = new SparkConf().setAppName("json").setMaster("local[4]");
        final JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2));
        jssc.sparkContext().setLogLevel("WARN");
        JavaReceiverInputDStream<String> stringJavaReceiverInputDStream = jssc.socketTextStream("node01", 9999);

        JavaDStream<List<R227Data>> listJavaDStream = stringJavaReceiverInputDStream.map(new Function<String, List<R227Data>>() {
            @Override
            public List<R227Data> call(String v1) throws Exception {
                JSONObject r227 = JSON.parseObject(v1).getJSONObject("R227");
                final List<R227Data> data = JSON.parseArray(r227.getString("data")).toJavaList(R227Data.class);
                return data;
            }
        });
        listJavaDStream.foreachRDD(rdd->{
            rdd.foreach(new VoidFunction<List<R227Data>>() {
                @Override
                public void call(List<R227Data> r227Data) throws Exception {
                    SparkSession spark = JavaSparkSessionSingleton.getInstance(sparkConf);
                    //通过映射关系  映射为表
                    Dataset<Row> wordsDataFrame = spark.createDataFrame(r227Data, R227Data.class);
                    // Creates a temporary view using the DataFrame
                    wordsDataFrame.createOrReplaceTempView("words");

                     spark.sql("SELECT CASEDATE totaltime FROM words").show();
                    final JavaRDD<Row> select_casedate_totaltime_from_words = spark.sql("SELECT CASEDATE totaltime FROM words").toJavaRDD();
                    select_casedate_totaltime_from_words.foreach(rdd->{
                        //[2017/06/19 00:00:00]
                        //[2015/03/27 00:00:00]
                        System.out.println(rdd.toString());
                    });
                    /* [totaltime: string]
                    String string = spark.sql("SELECT CASEDATE totaltime FROM words").toString();*/
                    System.out.println("========= " + 1 + "=========");
                }
            });

        });

        jssc.start();
        jssc.awaitTermination();
    }
}
