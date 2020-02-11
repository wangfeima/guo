package test;



import com.dfwy.online.sparkstreamingtask.vcloude.task.sfanalysis.JudgeAnalysisTask;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class JudgeAnynasisTest {
    public static void main(String[] args) throws Exception {

        String data = null;
        BufferedReader br = null;
        StringBuffer sb = null;

        String fileName = "D:\\工商司法信息数据样例.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
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
        JudgeAnalysisTask.judgeParseData();

    }
}
