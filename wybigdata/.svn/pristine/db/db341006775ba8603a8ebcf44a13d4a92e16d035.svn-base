package sparksql;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.streaming.Durations;

import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.util.Arrays;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/11/23 20:17
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: vcloude01
 * @pachage_name: sparksql
 * @description:
 */



public class sparkstreamingsql {
    //我的理解是把SPACE和" "所等效！
    private static final Pattern SPACE = Pattern.compile(" ");
    public static void main(String[] args) throws InterruptedException {
        SparkConf sparkConf = new SparkConf().setAppName("sparkstreamingsql").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2));
        jssc.sparkContext().setLogLevel("WARN");
        // nc -lk 9999  开始这个案例时，一定要把这个socket先开启！
        //千万要小心这个获取数据源的这个函数 socketTextStream 千万别再搞错了
        JavaReceiverInputDStream<String> lines = jssc.socketTextStream("node01", 9999);
        //JavaDStream<String> words = lines.flatMap(x -> Arrays.asList(SPACE.split(x)).iterator());
        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterator<String> call(String s) throws Exception {
                System.out.println(s);
                return Arrays.asList(SPACE.split(s)).iterator();
            }
        });
        words.foreachRDD((rdd, time) -> {
            // Convert JavaRDD[String] to JavaRDD[bean class] to DataFrame
            JavaRDD<JavaRecord> rowRDD = rdd.map(word -> {
                JavaRecord record = new JavaRecord();
                record.setWord(word);
                return record;
            });
            SparkSession spark = JavaSparkSessionSingleton.getInstance(rdd.context().getConf());
            //通过映射关系  映射为表
            Dataset<Row> wordsDataFrame = spark.createDataFrame(rowRDD, JavaRecord.class);
            // Creates a temporary view using the DataFrame
            wordsDataFrame.createOrReplaceTempView("words");
            //wordsDataFrame.registerTempTable("words");
            // Do word count on table using SQL and print it
            Dataset<Row> wordCountsDataFrame =spark.sql("select word, count(*) as total from words group by word");
            System.out.println("========= " + time + "=========");
            wordCountsDataFrame.show();
        });
        jssc.start();
        jssc.awaitTermination();
    }
}

/** Lazily instantiated singleton instance of SparkSession */
class JavaSparkSessionSingleton {
    private static transient SparkSession instance = null;
    public static SparkSession getInstance(SparkConf sparkConf) {
        if (instance == null) {
            instance = SparkSession
                    .builder()
                    .config(sparkConf)
                    .getOrCreate();
        }
        return instance;
    }
}
