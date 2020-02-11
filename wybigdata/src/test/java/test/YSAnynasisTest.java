package test;

import com.dfwy.online.sparkstreamingtask.vcloude.task.json.JsonAnalysis;
import com.dfwy.online.sparkstreamingtask.vcloude.task.etlclean.ElementsCreditETLInsertData;
import common.pojo.applicantinformation.ApplicantInformationTO;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class YSAnynasisTest {
    public static void main(String[] args) throws Exception {

        String data = null;
        BufferedReader br = null;
        StringBuffer sb = null;

        String fileName = "C:\\Users\\HP\\Desktop\\微银work\\微云平台\\微银\\数据\\元素征信报文.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
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
        System.out.println("data:" + data);
        ApplicantInformationTO applicantInformationTO = JsonAnalysis.json2Object(data);
        applicantInformationTO.setDataSourceFrom("elementscreditData");
        ElementsCreditETLInsertData.etlClean(applicantInformationTO);

    }
}
