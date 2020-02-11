package BackUp;

import com.google.gson.*;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.VoidFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;


public final class JavaDirectKafkaAnalyze2 {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) throws Exception {
        output();
    }

    public static void output() throws Exception {
        String brokers = "10.0.217.139:6667";
        String topics = "test_x";
        String groupId = "xgm";
        // Create context with a 2 seconds batch interval
        SparkConf sparkConf = new SparkConf().setAppName("JavaDirectKafkaAnalyze").setMaster("local[2]");
        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(2));

        Set<String> topicsSet = new HashSet<>(Arrays.asList(topics.split(",")));
        Map<String, Object> kafkaParams = new HashMap<>();
        kafkaParams.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, brokers);
        kafkaParams.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        kafkaParams.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaParams.put("auto.offset.reset", "earliest"); //earliest latest
        kafkaParams.put("enable.auto.commit", "false");


        // Create direct kafka stream with brokers and topics
        JavaInputDStream<ConsumerRecord<String, String>> messages = KafkaUtils.createDirectStream(
                jssc,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(topicsSet, kafkaParams));

        // Get the lines, split them into words, count the words and print
        JavaDStream<String> lines = messages.map(ConsumerRecord::value);
        //lines.print();

        JavaDStream<Map<String, Object>> datamap = lines.map(new GetLength2());

        JavaDStream<Map<String, Object>> ans = datamap.map(new GetAnswer2());


        ans.foreachRDD(new VoidFunction<JavaRDD<Map<String, Object>>>() {
            @Override
            public void call(JavaRDD<Map<String, Object>> mapJavaRDD) throws Exception {
                List<Map<String, Object>> mapList = mapJavaRDD.collect();
                List<JsonObject> list = JavaParseJson2.parse(mapList);
                //use kafka producer to send message to kafka
                MessageKafkaProducer2 producer2 = new MessageKafkaProducer2();
                for (JsonObject json : list) {
                    producer2.send(json.toString());
                    System.out.println(json.toString());
                }
            }
        });

        // Start the computation
        jssc.start();
        jssc.awaitTermination();

    }

    public static class JavaParseJson2 {
        public static List<JsonObject> parse(List<Map<String, Object>> mapList) {
            List<JsonObject> list = new ArrayList<JsonObject>();
            for (Map<String, Object> map : mapList) {
                JsonObject object = new JsonObject();
                String regCap = String.valueOf(map.get("regCap"));
                String enterpriseType = String.valueOf(map.get("enterpriseType"));
                String operatingYear = String.valueOf(map.get("operatingYear"));
                object.addProperty("regCap", regCap);
                object.addProperty("enterpriseType", enterpriseType);
                object.addProperty("operatingYear", operatingYear);
                list.add(object);
            }
            return list;
        }
    }

    public static class MessageKafkaProducer2 {
        private final KafkaProducer<String, String> producer;
        //声明要打入结果的kafka名字
        private final String topic = "test_xx";

        //    public MessageKafkaProducer(String topicName) {
        public MessageKafkaProducer2() {
            Properties props = new Properties();
            //props.put("bootstrap.servers", "10.0.254.20:9092,10.0.254.21:9092,10.0.254.18:9092");
            props.put("bootstrap.servers", "10.0.217.139:6667");
            props.put("acks", "all");
            props.put("retries", 0);
            props.put("batch.size", 16384);
            props.put("key.serializer", StringSerializer.class.getName());
            props.put("value.serializer", StringSerializer.class.getName());
            this.producer = new KafkaProducer<String, String>(props);
            //this.topic = topicName;
        }

        public void send(String message) {
            producer.send(new ProducerRecord<String, String>(topic, message));
        }


    }


    public static class GetAnswer2 implements Function<Map<String, Object>, Map<String, Object>> {
        @Override
        public Map<String, Object> call(Map<String, Object> stringObjectMap) throws Exception {

            LinkedHashMap map = new LinkedHashMap();


            //逻辑1
            Double regCap = new Double(stringObjectMap.get("regCap").toString());
            String regCapCur = stringObjectMap.get("regCapCur").toString();
            if (regCapCur.isEmpty() || regCapCur.equals("人民币")) {
                regCap = regCap * 10000;
                map.put("regCap", regCap);
            } else if (regCapCur.equals("美元")) {
                regCap = regCap * 10000 * 6.64;
                map.put("regCap", regCap);
            } else {
                map.put("regCap", null);
            }

            //逻辑2
            String type = (String) stringObjectMap.get("enterpriseType");
            if (type.contains("股份有限公司")) {
                map.put("enterpriseType", "01");
            } else if (type.contains("有限责任公司")) {
                map.put("enterpriseType", "02");
            } else if (type.contains("合伙企业")) {
                map.put("enterpriseType", "03");
            } else if (type.contains("个人独资企业")) {
                map.put("enterpriseType", "04");
            } else if (type.contains("个体")) {
                map.put("enterpriseType", "05");
            } else {
                map.put("enterpriseType", "06");
            }

            //逻辑3
            String esDate = (String) stringObjectMap.get("esDate");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date es = format.parse(esDate);
            long result = 0;
            result = (System.currentTimeMillis() - es.getTime()) / (24 * 60 * 60 * 1000) / 365;
            map.put("operatingYear", String.valueOf(result));

            return map;
        }
    }

    public static class GetLength2 implements Function<String, Map<String, Object>> {
        public Map<String, Object> call(String s) {
            // 解析json，获取BasicList
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(s);//获取根节点元素
            JsonObject root = element.getAsJsonObject();
            JsonObject R1103 = root.getAsJsonObject("R1103");
            JsonArray data = R1103.getAsJsonArray("data");
            JsonObject dataAsJsonObject = data.get(0).getAsJsonObject();
            JsonArray basicList = dataAsJsonObject.getAsJsonArray("basicList");

            // 从BasicList中获取regCapCur和regCap等数据
            JsonObject basicroot = basicList.get(0).getAsJsonObject();
            JsonPrimitive regCapCur = basicroot.getAsJsonPrimitive("regCapCur");
            JsonPrimitive regCap = basicroot.getAsJsonPrimitive("regCap");
            JsonPrimitive esDate = basicroot.getAsJsonPrimitive("esDate");
            JsonPrimitive enterpriseType = basicroot.getAsJsonPrimitive("enterpriseType");

            Map<String, Object> map = new HashMap();
            map.put("regCapCur", regCapCur.getAsString());
            map.put("regCap", regCap.getAsDouble());
            map.put("esDate", esDate.getAsString());
            map.put("enterpriseType", enterpriseType.getAsString());
            return map;
        }
    }


}