package HbaseTest;

import com.alibaba.fastjson.JSONObject;
import common.pojo.jsonfirstrootbean.JsonFirstRootBean;
import common.pojo.quotas.QuotasDataValueEntity;
import common.utils.date.DateUtils;
import common.utils.hbase.HBaseUtils;
import common.pojo.hbase.HBaseData;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.sql.catalyst.expressions.RowBasedKeyValueBatch;
import org.codehaus.jackson.annotate.JsonSubTypes;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.*;

import static common.constant.Constants.Dfwybank_VCloude_Hbase_BusinessJustice_TableName;

public class TFclode {
    public static void main(String[] args) throws Exception {
        String TableName = Dfwybank_VCloude_Hbase_BusinessJustice_TableName;
        String rowFilterName = "20181216";
        HBaseUtils.getCon();
        List<HBaseData> hBaseData = HBaseUtils.scanRowKeyFilter(TableName, rowFilterName);
        for (HBaseData hBaseDatum : hBaseData) {
            //System.out.println("RowKeyName:"+hBaseDatum.getRowKeyName());
            //System.out.println("ColumnFamily:"+hBaseDatum.getColumnFamily());
            //System.out.println("Column():"+hBaseDatum.getColumn());
            if ("record".equals(hBaseDatum.getColumn())) {
                System.out.println(hBaseDatum.getValue());
            }
            //System.out.println("TimeStamp:"+hBaseDatum.getTimeStamp());
        }
        HBaseUtils.close();
    }

    @Test
    public void aVoid(){
        String TableName = Dfwybank_VCloude_Hbase_BusinessJustice_TableName;
        String rowFilterName = "20181216";
        try {
            HBaseUtils.getCon();
             List<HBaseData> hBaseData = HBaseUtils.scanRowKeyFilter(TableName, rowFilterName);
            for (HBaseData hBaseDatum : hBaseData) {
                System.out.println("RowKeyName:"+hBaseDatum.getRowKeyName());
                System.out.println("ColumnFamily:"+hBaseDatum.getColumnFamily());
                System.out.println("Column:"+hBaseDatum.getColumn());
                System.out.println("Value:"+hBaseDatum.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            HBaseUtils.close();
        }


    }




    @Test
    public void HbaseScan() throws Exception {
        String tableName=Dfwybank_VCloude_Hbase_BusinessJustice_TableName;
        HBaseUtils.getCon();
        //20181207054434

        for (int i=0;i<20;i++) {
            Thread.sleep(1000);
            String yyyyMMddhhmmss = DateUtils.getUserDate("yyyyMMddhhmmss");
            System.out.println(yyyyMMddhhmmss);
            String rowKey="dfwy_date"+yyyyMMddhhmmss;
            System.out.println(i);
            HBaseUtils.putData("Dfwybank_BusinessJustice", rowKey, "data", "a" + String.valueOf(i), String.valueOf(i));
        }
        HBaseUtils.close();
    }

    @Test
    /**
     dfwy20181207060730
     dfwy20181207060732
     dfwy20181207060733
     dfwy20181207060734
     dfwy20181207060735
     dfwy20181207060736
     dfwy20181207060737
     dfwy20181207060739
     这个范围比较是因为withStartRow和withStopRow必须要从rowkey的头部开始
     不可进行中间查询
     */
    public void HbaseScanStart() throws Exception {
        Connection conn = HBaseUtils.getCon();
        Scan scan = new Scan();
        scan.withStartRow(Bytes.toBytes("date2018120809233"));
        scan.withStopRow(Bytes.toBytes("date20181208092339" ));
        String tableName="Dfwybank_BusinessJustice";

        Table table = conn.getTable(TableName.valueOf(tableName));
        ResultScanner result = table.getScanner(scan);
        for (Result r : result) {
            final Cell[] cells = r.rawCells();
            for (Cell cell : cells) {
                System.out.println(new String(CellUtil.cloneRow(cell)));
            }
        }
        table.close();
        HBaseUtils.close();
    }
    @Test
    /**
     * 想测试Table.exist是否存在这个功能来着
     */
    public void createTableTest() throws Exception {
        Connection conn = HBaseUtils.getCon();
        Admin admin = conn.getAdmin();
        if (admin.tableExists(TableName.valueOf("dfwybankD"))) {
            System.err.println("此表已经存在！");
        } else {
            ArrayList<String> columnFamilys = new ArrayList<>();
            columnFamilys.add("data");
            columnFamilys.add("info");
            HBaseUtils.createTable("dfwybankD", columnFamilys);
        }
        HBaseUtils.close();
    }

    @Test
    public void createTableTest1() throws Exception {
        HBaseUtils.getCon();
        ArrayList<String> columnFamilys = new ArrayList<>();
        columnFamilys.add("data");
        columnFamilys.add("info");
        HBaseUtils.createTable("dfwybankD",columnFamilys);
        HBaseUtils.close();
    }

    /**反射
     * @throws ClassNotFoundException
     */
    @Test
    public void testClass() throws ClassNotFoundException {
        JsonFirstRootBean jsonFirstRootBean = new JsonFirstRootBean();
        jsonFirstRootBean.setAmarsoftData("12345678");
        Class c = Class.forName(JsonFirstRootBean.class.getName());
        //获取该类的成员变量
        Field[] declaredFields = c.getDeclaredFields();
        for (Field field : declaredFields) {
            boolean flag = field.isAccessible();
            try {
                //设置该属性总是可访问
                field.setAccessible(true);
                System.out.println("成员变量" + field.getName() + "的值为：" + field.get(jsonFirstRootBean));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            //还原可访问权限
            field.setAccessible(flag);
        }
    }
   @Test
    public void putongvalue() {
        try {
            Connection con = HBaseUtils.getCon();
            HBaseUtils.putData("dfwybankD", "dfwyRowKey01", "info", "a", "b");
            HBaseUtils.putData("dfwybankD", "dfwyRowKey01", "info", "c", "d");
            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}