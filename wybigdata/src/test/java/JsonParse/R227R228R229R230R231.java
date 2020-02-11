package JsonParse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.utils.json.common.FirstParse;
import common.utils.json.r227.R227Parse;
import common.utils.json.r228.R228Parse;
import common.utils.json.r229.R229Parse;
import common.utils.json.r230.R230Parse;
import common.utils.json.r231.R231Parse;
import common.pojo.r227.R227Data;
import common.pojo.r228.R228Data;
import common.pojo.r229.R229Data;
import common.pojo.r230.R230Data;
import common.pojo.r231.R231Data;
import common.utils.stringutils.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

public class R227R228R229R230R231 {
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
//        System.out.println("数据为==> " + data);
    }


    @Test
    public void R227() {
        String code = FirstParse.getData(data, "R227R228R229R230R231", "code");
        String msg = FirstParse.getData(data, "R227R228R229R230R231", "msg");
        String r227Data = FirstParse.getData(data, "R227R228R229R230R231", "data");
        System.out.println(code);
        List<R227Data> r227DataList = R227Parse.getR227Object(r227Data);
        for (R227Data r227Data1 : r227DataList) {
            System.out.println(r227Data1.getCASEREASON());
        }
    }

    @Test
    public void R228() {
        String code = FirstParse.getData(data, "R228", "code");
        String msg = FirstParse.getData(data, "R228", "msg");
        String r228Data = FirstParse.getData(data, "R228", "data");
        System.out.println(code);
        List<R228Data> r228DataList = R228Parse.getR228Object(r228Data);
        for (R228Data r228Data1 : r228DataList) {
            System.out.println(r228Data1.getPNAME());
        }
    }



    @Test
    public void R229() {
        String code = FirstParse.getData(data, "R229", "code");
        String msg = FirstParse.getData(data, "R229", "msg");
        String r229Data = FirstParse.getData(data, "R229", "data");
        System.out.println(code);
        List<R229Data> r229DataList = R229Parse.getR229Object(r229Data);
        for (R229Data r229Data1 : r229DataList) {
            System.out.println(r229Data1.getPNAME());
        }
    }


    @Test
    public void R230() {
        String code = FirstParse.getData(data, "R230", "code");
        String msg = FirstParse.getData(data, "R230", "msg");
        String r230Data = FirstParse.getData(data, "R230", "data");
        System.out.println(code);
        List<R230Data> r230DataList = R230Parse.getR230Object(r230Data);
        for (R230Data r230Data1 : r230DataList) {
            System.out.println(r230Data1.getAREANAME());
        }
    }


    @Test
    public void R231() {
        String code = FirstParse.getData(data, "R231", "code");
        String msg = FirstParse.getData(data, "R231", "msg");
        String r231Data = FirstParse.getData(data, "R231", "data");
        System.out.println(code);
        List<R231Data> r231DataList = R231Parse.getR231Object(r231Data);
        for (R231Data r231Data1 : r231DataList) {
            System.out.println(r231Data1.getAGE());
        }
    }


    @Test
    public void testJson() {
        JSONObject jsonObject = JSON.parseObject(data);
        if (StringUtils.isNotEmpty(jsonObject.getString("amarsoftData"))) {
            JSONObject amarsoftData = jsonObject.getJSONObject("amarsoftData");
            System.out.println("amarsoftData："+amarsoftData.toString());
            Set<String> amarsoftDataKeySet = amarsoftData.keySet();
            JSONArray BDArray = new JSONArray();
            JSONArray JDArray = new JSONArray();
            JSONArray ODArray = new JSONArray();
            for (String s : amarsoftDataKeySet) {
                //Business data  工商数据 BD
                //Judicial data  司法数据 JD
                //Other data     其他数据 OD
                switch (s) {
                    case "R1103":
                        BDArray.add(amarsoftData.getJSONObject(s));
                        System.out.println("caseR1103:" + amarsoftData.getJSONObject(s).toString());
                        System.out.println("------------------------------------------------------------------------------------------------");
                        break;
                    case "R1104":
                        BDArray.add(amarsoftData.getJSONObject(s));
                        System.out.println("caseR1104:" + amarsoftData.getJSONObject(s).toString());
                        System.out.println("------------------------------------------------------------------------------------------------");
                        break;
                    case "R227":
                        JDArray.add(amarsoftData.getJSONObject(s));
                        System.out.println("caseR227:" + amarsoftData.getJSONObject(s).toString());
                        System.out.println("------------------------------------------------------------------------------------------------");
                        break;
                    case "R228":
                        JDArray.add(amarsoftData.getJSONObject(s));
                        System.out.println("caseR228:" + amarsoftData.getJSONObject(s).toString());
                        System.out.println("------------------------------------------------------------------------------------------------");
                        break;
                    case "R229":
                        JDArray.add(amarsoftData.getJSONObject(s));
                        System.out.println("caseR229:" + amarsoftData.getJSONObject(s).toString());
                        System.out.println("------------------------------------------------------------------------------------------------");
                        break;
                    case "R230":
                        JDArray.add(amarsoftData.getJSONObject(s));
                        System.out.println("caseR230:" + amarsoftData.getJSONObject(s).toString());
                        System.out.println("------------------------------------------------------------------------------------------------");
                        break;
                    case "R231":
                        JDArray.add(amarsoftData.getJSONObject(s));
                        System.out.println("caseR231:" + amarsoftData.getJSONObject(s).toString());
                        System.out.println("------------------------------------------------------------------------------------------------");
                        break;
                    default:
                        ODArray.add(amarsoftData.getJSONObject(s));
                        System.out.println("casedefault:" + amarsoftData.getJSONObject(s).toString());
                        System.out.println("------------------------------------------------------------------------------------------------");
                        break;
                }
            }
        }
    }
}
