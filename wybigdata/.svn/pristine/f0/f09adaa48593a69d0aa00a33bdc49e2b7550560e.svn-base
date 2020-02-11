package common.utils.json.r1104;

import common.utils.json.common.FirstParse;
import common.pojo.r1104.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class R1104Parse extends FirstParse {
    public static List<R1104Data> getR1104Object(String dataStr) {
        //第一、定义的是R1104对象中成员变量类型是List<R227Data>的成员变量r1104DataList
        List<R1104Data> r1104R1104DataList = new ArrayList<>();
        //第二、定义的是R1104对象中成员变量类型是List<R227Data>的成员变量r1104DataList的元素对象r1104Data
        R1104Data r1104R1104Data = new R1104Data();
        //第三、定义的是R1104对象中成员变量类型是List<R227Data>的成员变量r1104DataList的元素对象r1104Data中的各个数组成员变量
        List<RyPosFr> ryPosFrList = new ArrayList<>();
        List<RyPosPer> ryPosPerList = new ArrayList<>();
        List<RyPosSha> ryPosShaList = new ArrayList<>();
        //获取R1104-data中的JSON字符串中的JSON对象数组
        JSONArray r1104DataJsonObjectArray = JSON.parseArray(dataStr);

        //对R1104Json中Data数据中的Json数组数据的遍历
        for (Object r1104DataObject : r1104DataJsonObjectArray) {
            //对R1104Json中Data数据中的Json数组数据的元素进行对象化处理
            JSONObject r1104DataJsonObject = JSON.parseObject(r1104DataObject.toString());
            //获取ryPosFrList对象
            String ryPosFrListJsonStr = r1104DataJsonObject.get("ryPosFrList").toString();
            //将basicListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray ryPosFrListJsonObjectArray = JSON.parseArray(ryPosFrListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            ryPosFrList.clear();
            for (Object ryPosFrListObject : ryPosFrListJsonObjectArray) {
                RyPosFr ryPosFr = JSON.parseObject(ryPosFrListObject.toString(), RyPosFr.class);
                ryPosFrList.add(ryPosFr);
            }
            r1104R1104Data.setRyPosFrList(ryPosFrList);
            //获取ryPosPer对象
            String ryPosPerListJsonStr = r1104DataJsonObject.get("ryPosPerList").toString();
            //将basicListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray ryPosPerListJsonObjectArray = JSON.parseArray(ryPosPerListJsonStr);
            //遍历每个JSON数组获取PersonList对象,同时也要把数组清空，不然会出现重复数据
            ryPosPerList.clear();
            for (Object ryPosPerListObject : ryPosPerListJsonObjectArray) {
                RyPosPer ryPosPer = JSON.parseObject(ryPosPerListObject.toString(), RyPosPer.class);
                ryPosPerList.add(ryPosPer);
            }
            r1104R1104Data.setRyPosPerList(ryPosPerList);

            //获取ryPosShaList对象
            String ryPosShaListJsonStr = r1104DataJsonObject.get("ryPosShaList").toString();
            //将ryPosShaListStr这个JSON字符串转换成JSONArray以后进行对象化映射
            JSONArray ryPosShaListJsonObjectArray = JSON.parseArray(ryPosShaListJsonStr);
            //遍历每个JSON数组获取ryPosShaList对象,同时也要把数组清空，不然会出现重复数据
            ryPosShaList.clear();
            for (Object ryPosShaListObject : ryPosShaListJsonObjectArray) {
                RyPosSha ryPosSha = JSON.parseObject(ryPosShaListObject.toString(), RyPosSha.class);
                ryPosShaList.add(ryPosSha);
            }
            r1104R1104Data.setRyPosShaList(ryPosShaList);

            r1104R1104DataList.add(r1104R1104Data);
        }

        return r1104R1104DataList;
    }

}
