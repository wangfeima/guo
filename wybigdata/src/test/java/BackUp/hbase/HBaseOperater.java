package BackUp.hbase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.pojo.jsonfirstrootbean.JsonFirstRootBean;
import common.utils.hbase.HBaseUtils;
import common.utils.md5utils.MD5Util;
import common.utils.stringutils.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Set;

public class HBaseOperater {
    /**
     * 在调用方法时，请判断record非null
     * @param record
     * @return
     */
    public static String sourceDataIntoHBase(String record) {
        //获取HBase数据库连接
        Connection conn = null;
        //获取HBase表连接
        Table table = null;
        //MD5设置编码
        String charset = "UTF-8";
        try {
            int sum=1/0;
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
            //rowKey取MD5编码的前8位拼接businessID和reqID
            String rowKey = MD5Util.MD5Encode(businessID + reqID, charset).substring(0, 8) + "_" + businessID + "_" + reqID;
            System.out.println("HBase数据库rowKey值：" + rowKey);
            //将数据储存到Put结构之中
            Put put = new Put(Bytes.toBytes(rowKey));
            //原始数据的整体数据的储存
            put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("record"), Bytes.toBytes(record));
            System.out.println("HBase数据库record值：" + record);
            //以下针对原始报文的简单解析处理，并入库
            //利用放射进行数据的加入
            Class c = Class.forName(JsonFirstRootBean.class.getName());
            //获取该类的成员变量
            Field[] declaredFields = c.getDeclaredFields();
            for (Field field : declaredFields) {
                //获取成员变脸访问权限
                boolean flag = field.isAccessible();
                //设置该属性总是可访问
                field.setAccessible(true);
                //设置firstRootBean成员变量非null标志
                boolean notEmpty= false;
                //判断数据是否非空非null
                if (!(field.get(firstRootBean) == null)) {
                    notEmpty = StringUtils.isNotEmpty(field.get(firstRootBean).toString());
                }
                if (notEmpty) {
                    put.addColumn(Bytes.toBytes("data"), Bytes.toBytes(field.getName()), Bytes.toBytes(field.get(firstRootBean).toString()));
                    System.out.println("成员变量" + field.getName() + "的值为：" + field.get(firstRootBean));
                }
                //还原可访问权限
                field.setAccessible(flag);
            }
            //将数据存入HBase数据库
            table.put(put);
        } catch (Exception e) {
            try {
                conn = HBaseUtils.getCon();
                table = conn.getTable(TableName.valueOf("dfwybankD"));
                JSONObject jsonObject = JSON.parseObject(record);
                //获取数据构建rowkey
                String reqID = jsonObject.getString("reqID");
                String businessID = jsonObject.getString("businessID");
                //rowKey取MD5编码的前8位拼接businessID和reqID
                String rowKey = MD5Util.MD5Encode(businessID + reqID, charset).substring(0, 8) + "_" + businessID + "_" + reqID;
                //rowkey的打印输出
                System.out.println("没有使用对象结构来梳理数据HBase数据库rowKey值：" + rowKey);
                //将数据储存到Put结构之中
                Put put = new Put(Bytes.toBytes(rowKey));
                //原始数据的整体数据的储存
                put.addColumn(Bytes.toBytes("data"), Bytes.toBytes("record"), Bytes.toBytes(record));
                System.out.println("没有使用对象结构来梳理数据HBase数据库record值：" + record);
                //循环储存数据
                Set<String> keySet = jsonObject.keySet();
                for (String s : keySet) {
                    if (StringUtils.isNotEmpty(jsonObject.getString(s))) {
                        put.addColumn(Bytes.toBytes("data"), Bytes.toBytes(s), Bytes.toBytes(jsonObject.getString(s)));
                        System.out.println("没有使用对象结构来梳理数据HBase数据库" + s + "值：" + jsonObject.getString(s));
                    }
                }
            } catch (Exception e1) {
            }
            e.printStackTrace();
        } finally {
            try {
                //HBase表的关闭
                table.close();
                //HBase数据库的连接
                conn.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //准备做日志聚合输出使用
        return "";
    }
}
