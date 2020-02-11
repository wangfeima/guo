package common.utils.json.common;

import com.alibaba.fastjson.JSONObject;


public class FirstParse {
    /**
     * @param str      数据字符串
     * @param codeName R1103 R1104 R227 R228 R229 R230 R231
     * @param dataName code data msg
     * @return
     */

    public static String getData(String str, String codeName, String dataName) {
        String data = JSONObject.parseObject(str).getJSONObject(codeName).get(dataName).toString();
        return data;
    }

}


