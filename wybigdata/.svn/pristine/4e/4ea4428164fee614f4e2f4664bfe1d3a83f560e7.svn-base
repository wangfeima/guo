package com.dfwy.online.sparkstreamingtask.vcloude.task.kafka;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/10 11:55
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.http
 * @description:
 */


import common.utils.http.HttpRequestUtil;
import common.utils.exception.ErrorCodeIDE;
import common.utils.exception.ServiceException;
import common.utils.log.Log4jUtil;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.log4j.Logger;
import static common.constant.Constants.Dfwybank_VCloude_Http_Kafka_Consumer_Url;
import java.util.Arrays;
import java.util.Properties;

import static common.constant.Constants.*;

/**
 * 消费者对象不是线程安全的，
 * 也就是不能够多个线程同时使用一个消费者对象；
 * 而且也不能够一个线程有多个消费者对象。
 * 简而言之，一个线程一个消费者，
 * 如果需要多个消费者那么请使用多线程来进行一一对应。
 *
 * 在auto.offset.reset标志位为earliest/或者latest，
 * 上面的消费者都将在enable.auto.commit的基础之上进行继续消费
 * （如果enable.auto.commit=TRUE，则同一个消费者重启之后不会重复消费之前消费过的消息；
 * enable.auto.commit=FALSE，则消费者重启之后会消费到相同的消息）
 *
 * 原来这是对新的消费者而言（什么是新的消费者？比如说修改了上面消费者的GROUP_ID标识位）
 * 在auto.offset.reset=earliest情况下，新的消费者（消费者二）将会从头开始消费Topic下的消息
 * 在auto.offset.reset=latest情况下，新的消费者将会从其他消费者最后消费的offset处（offset=40开始）开始消费Topic下的消息
 */
public class KafkaConsumer {
    private static Logger logger = Logger.getLogger(KafkaConsumer.class.getName());
    private final org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer;
    private ConsumerRecords<String, String> msgList;
    private final String topic;

    public KafkaConsumer(String topicName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", Dfwybank_VCloude_Kafka_Bootstrap_Servers);
        props.put("group.id", Dfwybank_VCloude_Kafka_Consumer_Group);
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "latest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        this.consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(props);
        this.topic = topicName;
        this.consumer.subscribe(Arrays.asList(topic));
    }
    public void Send(){
        try {
            while (true) {
                msgList = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : msgList) {
                    //日志信息输出，后期要讲这个日志输出到HDFS
                    Log4jUtil.info(record.value());
                    System.out.println(record.value());
                    try {
                        String post = HttpRequestUtil.post(Dfwybank_VCloude_Http_Kafka_Consumer_Url, record.value());
                        //这里需要加返回数据的判断，如果正确如何，如果失败如何并抛出异常
                        Log4jUtil.info(post);
                        System.out.println(post);
                    }catch (Exception e){
                        Log4jUtil.logOutFormat(logger,e);
                        throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Http_SendData_ERROR
                                +"：Http发送数据异常！"
                                +System.getProperty("line.separator")
                                ,e);
                    }
                    //这个休眠时间还需要调优
                    Thread.sleep(200);
                }
            }
        } catch (Exception e) {
            //日志错误输出，后期要讲这个日志输出到HDFS
            Log4jUtil.logOutFormat(logger,e);
            throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Kafka_Consumer_ERROR
                    +"：Kafka消费数据异常！"
                    +System.getProperty("line.separator")
                    ,e);
        } finally {
            consumer.close();
        }
    }
}
