package com.dfwy.online.sparkstreamingtask.vcloude.main;

import com.dfwy.online.sparkstreamingtask.vcloude.task.kafka.KafkaConsumer;

import static common.constant.Constants.Dfwybank_VCloude_Kafka_Consumer_Topics;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/10 13:52
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.vcloude.main
 * @description:
 */
public class SendHttpMessage {
    public static void main(String[] args) {
        KafkaConsumer kafkaConsumer = new KafkaConsumer(Dfwybank_VCloude_Kafka_Consumer_Topics);
        kafkaConsumer.Send();
    }
}
