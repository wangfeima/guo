package common.utils.json.r230;

import common.utils.json.common.FirstParse;
import common.pojo.r230.R230Data;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class R230Parse extends FirstParse {
    public static List<R230Data> getR230Object(String dataStr) {
        //第一、定义的是R230对象中成员变量类型是List<R230Data>的成员变量r230DataList
        List<R230Data> r230DataList = new ArrayList<>();

        //获取R230-data中的JSON字符串中的JSON对象数组
        JSONArray r230DataJsonObjectArray = JSON.parseArray(dataStr);
        //对R230Json中Data数据中的Json数组数据的遍历
        for (Object r230DataJsonObject : r230DataJsonObjectArray) {
            //对R230Json中Data数据中的Json数组数据的元素进行对象化处理
            R230Data r230Data = JSON.parseObject(r230DataJsonObject.toString(), R230Data.class);
            r230DataList.add(r230Data);
        }

        return r230DataList;
    }

}
