package BackUp;

import com.alibaba.fastjson.JSON;
import common.pojo.jsonfirstrootbean.JsonFirstRootBean;
import common.utils.hbase.HBaseUtils;
import common.utils.md5utils.MD5Util;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;

public class HBaseOperater01 {
    /**
     * 在调用方法时，请判断record非null
     *
     * @param record
     * @return
     */
    public static String sourceDataIntoHBase(String record) {
        //获取HBase数据库连接
        Connection conn = null;
        //获取HBase表连接
        Table table = null;
        try {
            conn = HBaseUtils.getCon();
            table = conn.getTable(TableName.valueOf("dfwybankD"));
            JsonFirstRootBean firstRootBean = JSON.parseObject(record, JsonFirstRootBean.class);
            //获取对象数据
            String amarsoftData = firstRootBean.getAmarsoftData();
            String available = firstRootBean.getAvailable();
            String bankQuotaspool = firstRootBean.getBankQuotaspool();
            String entCreditID = firstRootBean.getEntCreditID();
            String entName = firstRootBean.getEntName();
            String indName = firstRootBean.getIndName();
            String productCode = firstRootBean.getProductCode();
            String uuid = firstRootBean.getUuid();
            String reqID = firstRootBean.getReqID();
            String businessID = firstRootBean.getBusinessID();
            //编辑rowkey
            String charset = "UTF-8";
            //决定加上reqID
            String s = MD5Util.MD5Encode("reqID".toString(), charset);
            //rowKey取MD5编码的前8位拼接businessID和reqID
            String rowKey = MD5Util.MD5Encode(businessID + reqID, charset).substring(0, 8) + "_" + businessID + "_" + reqID;
            //System.out.println("HBase数据库rowKey值：" + rowKey);
            //将数据储存到Put结构之中
            Put put = new Put(Bytes.toBytes(rowKey));
            //原始数据的整体数据的储存
            put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("record"), Bytes.toBytes(record));
            //System.out.println("HBase数据库record值：" + record);
            //以下针对原始报文的简单解析处理，并入库
            if (!(amarsoftData == null || amarsoftData.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("amarsoftData"), Bytes.toBytes(amarsoftData));
                //System.out.println("HBase数据库amarsoftData值：" + amarsoftData);
            }
            if (!(available == null || available.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("available"), Bytes.toBytes(available));
                //System.out.println("HBase数据库available值：" + available);
            }
            if (!(bankQuotaspool == null || bankQuotaspool.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("bankQuotaspool"), Bytes.toBytes(bankQuotaspool));
                System.out.println("HBase数据库bankQuotaspool值：" + bankQuotaspool);
            }
            if (!(entCreditID == null || entCreditID.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("entCreditID"), Bytes.toBytes(entCreditID));
                System.out.println("HBase数据库entCreditID值：" + entCreditID);
            }
            if (!(entName == null || entName.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("entName"), Bytes.toBytes(entName));
                System.out.println("HBase数据库entName值：" + entName);
            }
            if (!(productCode == null || productCode.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("productCode"), Bytes.toBytes(productCode));
                System.out.println("HBase数据库productCode值：" + productCode);
            }
            if (!(indName == null || indName.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("indName"), Bytes.toBytes(indName));
                System.out.println("HBase数据库indName值：" + indName);
            }
            if (!(uuid == null || uuid.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("uuid"), Bytes.toBytes(uuid));
                System.out.println("HBase数据库uuid值：" + uuid);
            }
            if (!(reqID == null || reqID.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("reqID"), Bytes.toBytes(reqID));
                System.out.println("HBase数据库reqID值：" + reqID);
            }
            if (!(businessID == null || businessID.equals(""))) {
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("businessID"), Bytes.toBytes(businessID));
                System.out.println("HBase数据库businessID值：" + businessID);
            }


            //将数据存入HBase数据库
            table.put(put);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //HBase表的关闭
            try {
                table.close();
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //准备做日志聚合输出使用
        return "";
    }





    public static void putHBase(String columnName, String data2Column, Put put) {
        if (!(columnName == null || columnName.equals(""))) {
            put.addColumn(Bytes.toBytes("data"), Bytes.toBytes(columnName), Bytes.toBytes(data2Column));
            System.out.println("HBase数据库" + columnName + "值：" + data2Column);
        }
    }

}
