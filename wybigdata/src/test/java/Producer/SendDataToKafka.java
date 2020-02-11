package Producer;


import org.apache.kafka.clients.producer.*;
import org.apache.kafka.clients.producer.KafkaProducer;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import static common.constant.Constants.*;
public class SendDataToKafka {
    private static String data;
    private static BufferedReader br = null;
    private static StringBuffer sb = null;
    private static final Map<String, String> codeStr = new HashMap<String, String>();

    public static void main(String[] args) throws Exception {
        int i = 0;
        while (i<1) {
            i += 1;
            //获取文本数据
            readText();
            try {
               //String dfwybank = sendMessage(data, "test_perf");
               //String dfwybank = sendMessage(data, "Dfwybank_VCloude_BusinessJustice_JSON_Topic_1");
               String dfwybank = sendMessage(data, "Dfwybank_VCloude_BusinessJustice_JSON_Topic");
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static String sendMessage(String messageStr, String TOPIC) throws ExecutionException, InterruptedException {
        //指定kafka producer topic
        String topic = TOPIC;
        //读取配置文件
        Properties props = new Properties();
        //kafka集群地址
        props.put("bootstrap.servers", Dfwybank_VCloude_Kafka_Bootstrap_Servers);
        //acks它代表消息确认机制,所有follower都响应了才认为消息提交成功
        //props.put("acks", "0");
        //重试的次数
        props.put("retries", 0);
        //批处理数据的大小，每次写入多少数据到topic，默认为16384=16KB
        props.put("batch.size", 16*1024);
        //使得producer将一直等待缓冲区直至其变为可用。否则如果producer生产速度过快耗尽了缓冲区，producer将抛出异常
        props.put("block.on.buffer.full", "true");
        //限制客户端在单个连接上能够发送的未响应请求的个数。
        //设置此值是1表示kafka broker在响应请求之前client不能再向同一个broker发送请求。
        // 注意：设置此参数是为了避免消息乱序
        //props.put("max.in.flight.requests.per.connection",1);
        //关闭unclean leader选举，即不允许非ISR中的副本被选举为leader，以避免数据丢失
        props.put("unclean.leader.election.enable", "false");
        //参考了Hadoop及业界通用的三备份原则---->这个有问题，因为如果你的备份数没有那么多，又该怎么设置！！！
        props.put("replication.factor ", 1);
        //消息至少要被写入到这么多副本才算成功，也是提升数据持久性的一个参数。与acks配合使用
        props.put("min.insync.replicas", 1);
        //关闭自动提交位移
        props.put("enable.auto.commit", "false");

        //可以延长多久发送数据
        props.put("linger.ms", 1);
        //缓冲区的大小
        props.put("buffer.memory", 32*1024*1024);
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
                    codeStr.put("message ", "失败");
                    codeStr.put("code ", "1");
                    codeStr.put("data ", e.getMessage());
                    e.printStackTrace();
                } else {
                    codeStr.put("message ", "成功");
                    codeStr.put("code ", "0");
                    codeStr.put("data ", "\"\"");
                }
            }
        });
        producer.close();
        return codeStr.toString();
    }



    public static void readText() throws Exception {
          /* 读入TXT文件*/
        // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        //String fileName = "C:\\Users\\User\\Desktop\\东方微银\\微云技术团队\\报文格式\\工商司法信息数据样例.txt";
        String fileName = "D:\\工商司法信息数据样例.txt";
        //读取文件
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "utf-8")); //这里可以控制编码
            sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        data = new String(sb); //StringBuffer ==> String
//        System.out.println("数据为==> " + data);
    }
}