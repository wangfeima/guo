package com.dfwy.online.sparkstreamingtask.vcloude.task.kafka;

/**
 * @author: yxx
 * @version: V-Cloude
 * @date: 2018/12/10 11:25
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.vcloude.task
 * @description:
 */
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.Future;

import static common.constant.Constants.Dfwybank_VCloude_SparkStreaming_Kafka_Producer_Topics;
import static common.constant.Constants.Dfwybank_VCloude_Kafka_Bootstrap_Servers;

public class SendDataToKafka {

    private static String message = "";

    public static String sendMessage(String messageStr) {
        //指定kafka producer topic
        String topic = Dfwybank_VCloude_SparkStreaming_Kafka_Producer_Topics;
        //读取配置文件
        Properties props = new Properties();
        //kafka集群地址
        props.put("bootstrap.servers", Dfwybank_VCloude_Kafka_Bootstrap_Servers);
        //acks它代表消息确认机制,所有follower都响应了才认为消息提交成功
        props.put("acks", "all");
        //重试的次数
        props.put("retries", 3);
        //批处理数据的大小，每次写入多少数据到topic
        props.put("batch.size", 1024*1024*20);
        //使得producer将一直等待缓冲区直至其变为可用。否则如果producer生产速度过快耗尽了缓冲区，producer将抛出异常
        props.put("block.on.buffer.full", "true");
        //限制客户端在单个连接上能够发送的未响应请求的个数。
        //设置此值是1表示kafka broker在响应请求之前client不能再向同一个broker发送请求。
        // 注意：设置此参数是为了避免消息乱序
        props.put("max.in.flight.requests.per.connection",1);
        //关闭unclean leader选举，即不允许非ISR中的副本被选举为leader，以避免数据丢失
        props.put("unclean.leader.election.enable", "false");
        //参考了Hadoop及业界通用的三备份原则
        props.put("replication.factor ", 3);
        //消息至少要被写入到这么多副本才算成功，也是提升数据持久性的一个参数。与acks配合使用
        props.put("min.insync.replicas", 2);
        //关闭自动提交位移
        props.put("enable.auto.commit", "false");
        //可以延长多久发送数据
        props.put("linger.ms", 1);
        //缓冲区的大小
        props.put("buffer.memory", 1024*1024*32);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //通过配置文件，创建生产者
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(props);
        //生产数据
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, messageStr);
        Future<RecordMetadata> recordMetadataFuture = producer.send(record, new Callback() {
            @Override
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if (e != null) {
                    message = "{\"message\":\"失败\",\"code\":\"1\",\"data\":\"" + e.getMessage() + "\"}";
                    e.printStackTrace();
                } else {
                    message = "{\"message\":\"成功\",\"code\":\"0\",\"data\":\"\"}";
                }
            }
        });
        producer.close();
        return message;
    }
}
