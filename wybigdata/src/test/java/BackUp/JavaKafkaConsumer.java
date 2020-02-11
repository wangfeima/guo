package BackUp;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;



public class JavaKafkaConsumer {

    private final KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> msgList;
    private final String topic;
    private static final String GROUPID = "groupe";

    public JavaKafkaConsumer(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "10.0.217.139:6667,10.0.217.203:6667");
        props.put("group.id", GROUPID);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        this.consumer = new KafkaConsumer<String, String>(props);
        this.topic = topicName;
        this.consumer.subscribe(Arrays.asList(topic));
    }


    public void Send(){
        try {
            while (true) {
                msgList = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : msgList) {
                    // System.out.print(record.value());
                    //sendPost("http://10.0.217.139:1234",record.value(),"utf-8");
                    System.out.println();
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }
    public static String sendPost(String url, String param,String charset) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connx = realUrl.openConnection();
            conn = (HttpURLConnection)connx;
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(60);
            conn.setReadTimeout(60);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);

            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);

            }

            result=sb.toString();

            System.out.println(result);

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                if(out!=null){
                    out.close();
                    System.out.println("close out");
                }
                if(in!=null){
                    System.out.println("close in");
                    in.close();
                }
                conn.disconnect();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    public static void main(String args[]) {
        JavaKafkaConsumer javaKafkaConsumer = new JavaKafkaConsumer("test_xx");
        javaKafkaConsumer.Send();
    }

}
