package common.utils.json.r227;

import common.utils.json.common.FirstParse;
import common.pojo.r227.R227Data;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class R227Parse extends FirstParse {
    public static List<R227Data> getR227Object(String dataStr) {
        //第一、定义的是R227对象中成员变量类型是List<R227Data>的成员变量r227DataList
        List<R227Data> r227DataList = new ArrayList<>();

        //获取R227-data中的JSON字符串中的JSON对象数组
        JSONArray r227DataJsonObjectArray = JSON.parseArray(dataStr);
        //对R227Json中Data数据中的Json数组数据的遍历
        for (Object r227DataJsonObject : r227DataJsonObjectArray) {
            //对R227Json中Data数据中的Json数组数据的元素进行对象化处理
            R227Data r227Data = JSON.parseObject(r227DataJsonObject.toString(), R227Data.class);
            r227DataList.add(r227Data);
        }

        return r227DataList;
    }

}
