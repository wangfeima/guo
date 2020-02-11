package http;


import common.utils.http.HttpRequestUtil;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/9 14:42
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: http
 * @description:此工程验证了spark发送http请求的可行性！但是有一点就是需要等待http service的响应！
 */
public class httpcli {
    public static void main(String[] args) throws InterruptedException {
        final SparkConf sparkConf = new SparkConf().setAppName("json").setMaster("local[4]");
        final JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2));
        //jssc.sparkContext().setLogLevel("WARN");
        JavaReceiverInputDStream<String> stringJavaReceiverInputDStream = jssc.socketTextStream("node01", 9999);

        stringJavaReceiverInputDStream.foreachRDD(rdd -> {
            rdd.foreach(new VoidFunction<String>() {
                @Override
                public void call(String s) throws Exception {
                    final String post = HttpRequestUtil.post("http://localhost:1000/ABC", "{董a1:"+s+",董a3:s2}");
                    System.out.println(s);
                    System.out.println(post);
                }
            });
        });

        jssc.start();
        jssc.awaitTermination();
    }
}
