package JsonParse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/12 10:31
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: JsonParse
 * @description:
 */
public class JsonText {

    @Test
    //FastJson构建JsonString
    public void fastJsonText(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "张三");
        jsonObject.put("age", new Integer(26));

        JSONObject innerObj = new JSONObject();
        innerObj.put("university", "某某学校");
        innerObj.put("special", "某某专业");

        JSONObject innerObj2 = new JSONObject();
        innerObj2.put("university", "2某某学校");
        innerObj2.put("special", "2某某专业");

        JSONObject innerObj3 = new JSONObject();
        innerObj3.put("university", "3某某学校");
        innerObj3.put("special", "3某某专业");

        JSONArray innerObjArr=new JSONArray();
        innerObjArr.add(innerObj3);
        innerObjArr.add(innerObj2);

        jsonObject.put("education", innerObj);
        jsonObject.put("array",innerObjArr);

        //{"education":{"special":"某某专业","university":"某某学校"},"array":[{"special":"3某某专业","university":"3某某学校"},{"special":"2某某专业","university":"2某某学校"}],"name":"张三","age":26}
        System.out.println(jsonObject.toString());
    }
}
