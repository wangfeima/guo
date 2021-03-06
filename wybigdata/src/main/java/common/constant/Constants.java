package common.constant;

import java.util.Arrays;
import java.util.Collection;

/**
 * 常量接口
 * @author
 */
public interface Constants {
    //一期kafka参数地址设置
    //4台测试平台地址
    //String Dfwybank_VCloude_Kafka_Bootstrap_Servers = "119.254.155.32:6667,119.254.155.209:6667";
    //本地虚拟机地址
    //String Dfwybank_VCloude_Kafka_Bootstrap_Servers = "node01:9092,node03:9092";
    //7台测试平台kafka内网地址
    //String Dfwybank_VCloude_Kafka_Bootstrap_Servers = "10.10.0.14:6667,10.10.0.15:6667";
    //7台测试平台kafka外网地址
    //String Dfwybank_VCloude_Kafka_Bootstrap_Servers = "data-4:6667,data-5:6667";
    //7台测试平台kafka外网地址
    String Dfwybank_VCloude_Kafka_Bootstrap_Servers = "dev-4:6667";
    //Spark集群含有消费者，从Kafka集群拉取数据（对接消费报文数据的topic)
    Collection<String> Dfwybank_VCloude_SparkStreaming_Kafka_Consumer_Topics = Arrays.asList("Dfwybank_VCloude_BusinessJustice_JSON_Topic");
    //Spark集群含有生产者，向Kafka集群发送数据（对接发送http请求的topic）
    //String  Dfwybank_VCloude_SparkStreaming_Kafka_Producer_Topics = "Dfwybank_VCloude_BusinessJustice_Http_Topic";
    String  Dfwybank_VCloude_SparkStreaming_Kafka_Producer_Topics = "Dfwybank_VCloude_BusinessJustice_Http_Topic";
    //单独的打包程序作为消费者，从Kafka集群拉取数据消费并发送http请求（对接消费报文数据的topic）
    String Dfwybank_VCloude_Kafka_Consumer_Group="Dfwybank_VCloude_BusinessJustice_Http_Group";
    String Dfwybank_VCloude_Kafka_Consumer_Topics = "Dfwybank_VCloude_BusinessJustice_Http_Topic";
    //httpcli  测试环境下的http发送的http请求服务
    String Dfwybank_VCloude_Http_Kafka_Consumer_Url="http://192.168.20.242:1000/abc";
    String Dfwybank_VCloude_Http_Kafka_Consumer_Charset="utf-8";
    //一期HBase参数地址设置
    //4台测试集群平台地址
    //String Dfwybank_VCloude_Hbase_Zookeeper_Quorum = "dev-2,dev-3,dev-4";
    //本地虚拟机平台地址
    //String Dfwybank_VCloude_Hbase_Zookeeper_Quorum = "node01,node02,node03";
    //7台测试集群平台地址
    //内网zookeeper地址
    //String Dfwybank_VCloude_Hbase_Zookeeper_Quorum = "data-3,data-4,data-5";
    String Dfwybank_VCloude_Hbase_Zookeeper_Quorum = "dev-3,dev-4";
    //外网zookeeper地址
    //String Dfwybank_VCloude_Hbase_Zookeeper_Quorum = "119.254.155.91,119.254.155.32,";
    String Dfwybank_VCloude_Hbase_Zookeeper_Property_ClientPort = "2181";
    //String Dfwybank_VCloude_Hbase_Zookeeper_Znode_Parent = "/online.streaming.hbase-unsecure";
    String Dfwybank_VCloude_Hbase_Zookeeper_Znode_Parent = "/hbase-unsecure";
    //HBase 数据库中工商司法表
    //HBase工商表
    String Dfwybank_VCloude_Hbase_BusinessJustice_TableName="Dfwybank_VCloude_BusinessJustice";
    //HBase工商表中的三个列族1、整体报文数据2、仅仅安硕、忠诚信、元素的报文数据3、计算结果
    String Dfwybank_VCloude_Hbase_BusinessJustice_ColumnFamily_1="data";
    String Dfwybank_VCloude_Hbase_BusinessJustice_ColumnFamily_2="info";
    String Dfwybank_VCloude_Hbase_BusinessJustice_ColumnFamily_3="result";

    String Dfwybank_VCloude_Hbase_BusinessJustice_DateSourceKey_1="amarsoftData";
    String Dfwybank_VCloude_Hbase_BusinessJustice_DateSourceKey_2="ccxData";
    String Dfwybank_VCloude_Hbase_BusinessJustice_DateSourceKey_3="elementscreditData";

    String Dfwybank_VCloude_Hbase_BusinessJustice_ColumnFamily_ColumnName_1="amarsoft";
    String Dfwybank_VCloude_Hbase_BusinessJustice_ColumnFamily_ColumnName_2="ccx";
    String Dfwybank_VCloude_Hbase_BusinessJustice_ColumnFamily_ColumnName_3="elements";

    //SparkAppName  BJ--->BusinessJustice缩写
    String Dfwybank_VCloude_SparkStreaming_BJAppName="Dfwybank_VCloude_SparkStreaming_BJ";
}
