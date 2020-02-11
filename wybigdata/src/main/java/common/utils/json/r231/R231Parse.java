package common.utils.json.r231;

import common.utils.json.common.FirstParse;
import common.pojo.r231.R231Data;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class R231Parse extends FirstParse {
    public static List<R231Data> getR231Object(String dataStr) {
        //第一、定义的是R231对象中成员变量类型是List<R231Data>的成员变量r231DataList
        List<R231Data> r231DataList = new ArrayList<>();

        //获取R231-data中的JSON字符串中的JSON对象数组
        JSONArray r231DataJsonObjectArray = JSON.parseArray(dataStr);
        //对R231Json中Data数据中的Json数组数据的遍历
        for (Object r231DataJsonObject : r231DataJsonObjectArray) {
            //对R231Json中Data数据中的Json数组数据的元素进行对象化处理
            R231Data r231Data = JSON.parseObject(r231DataJsonObject.toString(), R231Data.class);
            r231DataList.add(r231Data);
        }

        return r231DataList;
    }

}
