package common.utils.json.r229;

import common.utils.json.common.FirstParse;
import common.pojo.r229.R229Data;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class R229Parse extends FirstParse {
    public static List<R229Data> getR229Object(String dataStr) {
        //第一、定义的是R229对象中成员变量类型是List<R229Data>的成员变量r229DataList
        List<R229Data> r229DataList = new ArrayList<>();

        //获取R229-data中的JSON字符串中的JSON对象数组
        JSONArray r229DataJsonObjectArray = JSON.parseArray(dataStr);
        //对R229Json中Data数据中的Json数组数据的遍历
        for (Object r229DataJsonObject : r229DataJsonObjectArray) {
            //对R229Json中Data数据中的Json数组数据的元素进行对象化处理
            R229Data r229Data = JSON.parseObject(r229DataJsonObject.toString(), R229Data.class);
            r229DataList.add(r229Data);
        }

        return r229DataList;
    }

}
