package com.dfwy.online.sparkstreamingtask.vcloude.task.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import common.pojo.applicantinformation.ApplicantInformationTO;
import common.utils.beanrefutil.BeanRefUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/3 15:58
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.vcloude
 * @description:
 */
public class JsonAnalysis {
    public static ApplicantInformationTO json2Object(String record) throws ClassNotFoundException {
        //定义申请实体类
        ApplicantInformationTO informationTO=new ApplicantInformationTO();
        Map<String,String> map = new HashMap<String, String>();
        //获取Json报文
        JSONObject jsonObject = JSON.parseObject(record);
        //获取Json keySet集合
        Set<String> keySet = jsonObject.keySet();
        //循环遍历keySet 获得Json数值
        for (String s : keySet) {
            map.put(s,jsonObject.getString(s));
        }
        //利用BeanRefUtil工具类，将map中的数据值存入到申请实体类中
        BeanRefUtil.setFieldValue(informationTO, map);
        //返回申请实体类
        return informationTO;
    }
}
