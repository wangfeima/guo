package JsonParse;

import common.utils.json.common.FirstParse;
import common.pojo.r1104.R1104Data;
import common.pojo.r1104.RyPosFr;
import common.pojo.r1104.RyPosPer;
import common.pojo.r1104.RyPosSha;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

import static common.utils.json.r1104.R1104Parse.getR1104Object;

public class R1104 {
    private static String data;
    private BufferedReader br = null;
    private StringBuffer sb = null;

    @Before
    public void readText() throws Exception {
          /* 读入TXT文件 */
        String fileName = "C:\\Users\\User\\Desktop\\东方微银\\微云技术团队\\报文格式\\工商司法信息数据样例.txt"; // 绝对路径或相对路径都可以，这里是绝对路径，写入文件时演示相对路径
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
    }

    @Test
    public void ryPosFrList(){
        String code = FirstParse.getData(data, "R1104", "code");
        String msg = FirstParse.getData(data, "R1104", "msg");
        String r1104Data = FirstParse.getData(data, "R1104","data");
        System.out.println(code);
        List<R1104Data> r1104Object = getR1104Object(r1104Data);
        for (R1104Data r1104Data1 : r1104Object) {
            List<RyPosFr> RyPosFr = r1104Data1.getRyPosFrList();
            for (common.pojo.r1104.RyPosFr ryPosFr : RyPosFr) {
                System.out.println(ryPosFr.getEntName());
            }
        }
    }


    @Test
    public void ryPosPerList(){
        String code = FirstParse.getData(data, "R1104", "code");
        String msg = FirstParse.getData(data, "R1104", "msg");
        String r1104Data = FirstParse.getData(data, "R1104","data");
        System.out.println(code);
        List<R1104Data> r1104Object = getR1104Object(r1104Data);
        for (R1104Data r1104Data1 : r1104Object) {
            List<RyPosPer> RyPosFr = r1104Data1.getRyPosPerList();
            for (RyPosPer ryPosFr : RyPosFr) {
                System.out.println(ryPosFr.getRyName());
            }
        }
    }



    @Test
    public void ryPosShaList(){
        String code = FirstParse.getData(data, "R1104", "code");
        String msg = FirstParse.getData(data, "R1104", "msg");
        String r1104Data = FirstParse.getData(data, "R1104","data");
        System.out.println(code);
        List<R1104Data> r1104Object = getR1104Object(r1104Data);
        for (R1104Data r1104Data1 : r1104Object) {
            List<RyPosSha> ryPosShaList = r1104Data1.getRyPosShaList();
            for (RyPosSha ryPosSha : ryPosShaList) {
                System.out.println(ryPosSha.getEntName());
                System.out.println(ryPosSha.getEntStatus());
                System.out.println(ryPosSha.getEntType());
                System.out.println(ryPosSha.getRegCap());
                System.out.println(ryPosSha.getRegCapcur());
                System.out.println(ryPosSha.getRegNo());
                System.out.println(ryPosSha.getRyName());
                System.out.println(ryPosSha.getSubConam());
                System.out.println(ryPosSha.getSubCurrency());
            }
        }
    }




}
