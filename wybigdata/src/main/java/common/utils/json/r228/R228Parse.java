package common.utils.json.r228;

import common.utils.json.common.FirstParse;
import common.pojo.r228.R228Data;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class R228Parse extends FirstParse {
    public static List<R228Data> getR228Object(String dataStr) {
        //第一、定义的是R228对象中成员变量类型是List<R228Data>的成员变量r228DataList
        List<R228Data> r228DataList = new ArrayList<>();

        //获取R228-data中的JSON字符串中的JSON对象数组
        JSONArray r228DataJsonObjectArray = JSON.parseArray(dataStr);
        //对R228Json中Data数据中的Json数组数据的遍历
        for (Object r228DataJsonObject : r228DataJsonObjectArray) {
            //对R228Json中Data数据中的Json数组数据的元素进行对象化处理
            R228Data r228Data = JSON.parseObject(r228DataJsonObject.toString(), R228Data.class);
            r228DataList.add(r228Data);
        }

        return r228DataList;
    }

}
