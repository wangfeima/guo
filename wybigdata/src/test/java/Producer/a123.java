package Producer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2019/1/6 12:44
 * @copyright©: 2019东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.vcloude.task.kafka
 * @description:
 */
public class a123 {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "dev-4:6667");
        props.put("group.id", "as1");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "30000");
        props.put("auto.offset.reset", "earliest");
        props.put("key.deserializer", StringDeserializer.class.getName());
        props.put("value.deserializer", StringDeserializer.class.getName());
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(props);
        ConsumerRecords<String, String> msgList;
        consumer.subscribe(Arrays.asList("Dfwybank_VCloude_BusinessJustice_JSON_Topic"));
        try {
            while (true) {
                msgList = consumer.poll(1000);
                for (ConsumerRecord<String, String> record : msgList) {
                    System.out.println(record.value());
                    Thread.sleep(1000);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close();
        }
    }

    @Test
    public void aVoid() {
        // Producer 配置信息，应该配置在属性文件中
        Properties props = new Properties();
        //指定要连接的 broker，不需要列出所有的 broker，但建议至少列出2个，以防某个 broker 挂了
        props.put("bootstrap.servers", "119.254.155.209:6667");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("retries", 3); // 如果发生错误，重试三次
        props.put("acks", "1"); // 0：不应答，1：leader 应该，all：所有 leader 和 follower 应该

        // 创建 Producer
        Producer<String, String> producer = new KafkaProducer<String, String>(props);

        // send 方法是异步的，方法返回并不代表消息发送成功
        producer.send(new ProducerRecord<String, String>("topic0", "message 1"));

        // 如果需要确认消息是否发送成功，以及发送后做一些额外操作，有两种办法
        // 方法 1: 使用 callback
        producer.send(new ProducerRecord<String, String>("topic0", "message 2"), new Callback() {
            public void onCompletion(RecordMetadata metadata, Exception exception) {
                if (exception != null) {
                    System.out.println("send message2 failed with " + exception.getMessage());
                } else {
                    // offset 是消息在 partition 中的编号，可以根据 offset 检索消息
                    System.out.println("message2 sent to " + metadata.topic() + ", partition " + metadata.partition() + ", offset " + metadata.offset());
                }
            }
        });

/*        // 方法2：使用阻塞
        Future<RecordMetadata> sendResult = producer.send(new ProducerRecord<String, String>("topic0", "message 3"));
        try {
            // 阻塞直到发送成功
            RecordMetadata metadata = sendResult.get();
            System.out.println("message3 sent to " + metadata.topic() + ", partition " + metadata.partition() + ", offset " + metadata.offset());
        } catch (Exception e) {
            System.out.println("send message3 failed with " + e.getMessage());
        }*/

        // producer 需要关闭，放在 finally 里
        producer.close();
    }
}
