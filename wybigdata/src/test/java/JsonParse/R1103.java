package JsonParse;

import com.alibaba.fastjson.JSONObject;
import com.dfwy.online.sparkstreamingtask.business.basics.IdeQuotasBusiService;
import com.dfwy.online.sparkstreamingtask.business.basics.IdeQuotasBusiServiceImpl;
import com.dfwy.online.sparkstreamingtask.dao.IdeQuotasWriteDao;
import com.dfwy.online.sparkstreamingtask.vcloude.task.etlclean.AmarsoftETLInsertData;
import com.dfwy.online.sparkstreamingtask.vcloude.task.etlclean.CCXETLInsertData;
import com.dfwy.online.sparkstreamingtask.vcloude.task.etlclean.ElementsCreditETLInsertData;
import com.dfwy.online.sparkstreamingtask.vcloude.task.hbase.HBaseOperater;
import com.dfwy.online.sparkstreamingtask.vcloude.task.json.JsonAnalysis;
import com.dfwy.online.sparkstreamingtask.vcloude.task.json.JsonSplice;
import com.dfwy.online.sparkstreamingtask.vcloude.task.kafka.SendDataToKafka;
import com.dfwy.online.sparkstreamingtask.vcloude.task.sfanalysis.JudgeAnalysisTask;
import common.pojo.applicantinformation.ApplicantInformationTO;
import common.pojo.etlstandardtable.StdEntBasicList;
import common.pojo.hbase.HBaseData;
import common.pojo.quotas.QuotasDataValueEntity;
import common.utils.date.DateUtils;
import common.utils.exception.ErrorCodeIDE;
import common.utils.exception.ServiceException;

import com.alibaba.fastjson.JSON;
import common.utils.hbase.HBaseUtils;
import common.utils.md5utils.MD5Util;
import common.utils.sqlsessionfactoryutil.SqlSessionFactoryUtil;
import common.utils.stringutils.StringUtils;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.ibatis.session.SqlSession;
import org.apache.xerces.dom.PSVIAttrNSImpl;
import org.junit.Before;
import org.junit.Test;

import javax.sound.midi.Soundbank;
import javax.xml.bind.SchemaOutputResolver;
import java.io.*;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Vector;

import static common.constant.Constants.Dfwybank_VCloude_Hbase_BusinessJustice_ColumnFamily_1;
import static common.constant.Constants.Dfwybank_VCloude_Hbase_BusinessJustice_TableName;


public class R1103 {
    private static String data;
    private BufferedReader br = null;
    private StringBuffer sb = null;

    @Before
    public void readText() throws Exception {
          /* 读入TXT文件 */
        String fileName = "D:\\工商司法信息数据样例.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        //String fileName = "D:\\元素征信报文.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        //String fileName = "D:\\中诚信报文.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
        //读取文件D:\工商司法信息数据样例.txt
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
    }

    @Test
    public void localTest() throws Exception {
        final long l = System.currentTimeMillis();
        ApplicantInformationTO applicantInformationTO = JsonAnalysis.json2Object(data);
        //2、原始报文的数据清洗入库工作
        if (StringUtils.isNotEmpty(applicantInformationTO.getAmarsoftData())){
            AmarsoftETLInsertData.etlClean(applicantInformationTO);
        }else if(StringUtils.isNotEmpty(applicantInformationTO.getElementscreditData())){
            ElementsCreditETLInsertData.etlClean(applicantInformationTO);
        }else if(StringUtils.isNotEmpty(applicantInformationTO.getCcxData())){
            CCXETLInsertData.etlClean(applicantInformationTO);
        }else {
            throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Date_Source_From + "：数据来源数据为空！");
        }
        //3、司法解析
        //JudgeAnalysisTask.judgeParseData();
        //4、指标计算接口
        IdeQuotasBusiService ideQuotasBusiService = new IdeQuotasBusiServiceImpl();
        //4.1指标计算值对象集合获得
        Vector<QuotasDataValueEntity> dataValueList = ideQuotasBusiService.execQuotas(applicantInformationTO, "A", "1");
        //4.2拼接发送计算结果数据Json
        String jsonStr = JsonSplice.getJsonStr(dataValueList);
        System.out.println(jsonStr);
        //5、报文和计算结果存入HBase数据库 applicantInformationTO  dataValueList
       HBaseOperater.data2HBase(data, applicantInformationTO, jsonStr);
        /*//6、Http请求的发送
        Integer code = 0;
        String msg = SendDataToKafka.sendMessage(jsonStr);
        JSONObject jsonObject = JSONObject.parseObject(msg);
        code = jsonObject.getInteger("code");
        if (!(code == 0)) {
            throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Spark_Kafka_Producer_Send_ERROR + "：Kafka生产者发送失败！");
        }*/
         System.out.println(System.currentTimeMillis()-l);
    }

    @Test
    public void object2json() {
        HBaseData hBaseData = new HBaseData();
        hBaseData.setColumn("1");
        hBaseData.setColumnFamily("2");
        hBaseData.setRowKeyName("3");
        hBaseData.setTimeStamp("4");
        hBaseData.setValue("5");

        final Object o = JSON.toJSON(hBaseData);
        final String s = StringUtils.objToStr(o);
        System.out.println(s);
    }

    @Test
    public void a() throws ClassNotFoundException {
        ApplicantInformationTO applicantInformationTO = JsonAnalysis.json2Object(data);
        //指标计算接口
        IdeQuotasBusiService ideQuotasBusiService = new IdeQuotasBusiServiceImpl();
        //指标计算值对象集合获得
        Vector<QuotasDataValueEntity> dataValueList = ideQuotasBusiService.execQuotas(applicantInformationTO, "A", "1");
    }

    @Test
    public void elementsVoidString() throws Exception {
        //将传输过来的报文数据封装进申请人实例中，做数据的传输！
        ApplicantInformationTO applicantInformationTO = JsonAnalysis.json2Object(data);
        applicantInformationTO.setDataSourceFrom("elementscreditData");
        if (StringUtils.isNotEmpty(applicantInformationTO.getAmarsoftData())) {
            AmarsoftETLInsertData.etlClean(applicantInformationTO);
        } else if (StringUtils.isNotEmpty(applicantInformationTO.getElementscreditData())) {
            ElementsCreditETLInsertData.etlClean(applicantInformationTO);
        } else if (StringUtils.isNotEmpty(applicantInformationTO.getCcxData())) {
            CCXETLInsertData.etlClean(applicantInformationTO);
        } else {
            throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Date_Source_From + "：数据来源数据为空！");
        }
    }

    @Test
    public void cxxVoidString() throws Exception {
        //将传输过来的报文数据封装进申请人实例中，做数据的传输！
        ApplicantInformationTO applicantInformationTO = JsonAnalysis.json2Object(data);
        applicantInformationTO.setDataSourceFrom("cxxData");
        if (StringUtils.isNotEmpty(applicantInformationTO.getAmarsoftData())) {
            AmarsoftETLInsertData.etlClean(applicantInformationTO);
        } else if (StringUtils.isNotEmpty(applicantInformationTO.getElementscreditData())) {
            ElementsCreditETLInsertData.etlClean(applicantInformationTO);
        } else if (StringUtils.isNotEmpty(applicantInformationTO.getCcxData())) {
            CCXETLInsertData.etlClean(applicantInformationTO);
        } else {
            throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Date_Source_From + "：数据来源数据为空！");
        }
    }

    @Test
    public void aVoidSrring() throws Exception {
        //将传输过来的报文数据封装进申请人实例中，做数据的传输！
        ApplicantInformationTO applicantInformationTO = JsonAnalysis.json2Object(data);
        applicantInformationTO.setDataSourceFrom("amarsoftData");
        AmarsoftETLInsertData.etlClean(applicantInformationTO);
    }

    @Test
    public void bVoidString() throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchFieldException {
        StdEntBasicList stdEntBasicList = new StdEntBasicList();
        stdEntBasicList.setAddress("xiaoliujin!!!");
        /*Class clazz = Class.forName("common.pojo.etlstandardtable.StdEntBasicList");
        Object obj = clazz.newInstance();//通过反射创建对象*/
        // 调用getDeclaredField("name") 取得name属性对应的Field对象
        Field f = stdEntBasicList.getClass().getDeclaredField("address");
        // 取消属性的访问权限控制，即使private属性也可以进行访问。
        f.setAccessible(true);
        // 调用get()方法取得对应属性值。
        System.out.println(f.get(stdEntBasicList));  //相当于obj.getName();
        // 调用set()方法给对应属性赋值。
        f.set(stdEntBasicList, "lkl");  //相当于obj.setName("lkl");
        // 调用get()方法取得对应属性修改后的值。
        System.out.println(f.get(stdEntBasicList));
    }

    public static void main(String[] args) throws Exception {
        Connection  conn = HBaseUtils.getCon();
        HBaseUtils.putData("dfwy_test","dong","data","amarsoft","dong1");
        HBaseUtils.close();
    }

}


