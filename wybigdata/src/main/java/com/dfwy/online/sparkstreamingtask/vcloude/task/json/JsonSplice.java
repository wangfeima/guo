package com.dfwy.online.sparkstreamingtask.vcloude.task.json;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.pojo.quotas.QuotasDataValueEntity;
import org.apache.kafka.streams.kstream.ForeachAction;
import org.mortbay.util.ajax.JSON;
import scala.tools.nsc.backend.icode.Opcodes;

import java.util.Vector;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/9 21:26
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.vcloude.task
 * @description:
 */
public class JsonSplice {
    public static String getJsonStr(Vector<QuotasDataValueEntity> dataValueList){
        JSONObject jsonObject= new JSONObject();
        JSONArray innerObjArr=new JSONArray();
        for (QuotasDataValueEntity quotasDataValueEntity : dataValueList) {
            JSONObject quotasDataValueEntityJsonObject = (JSONObject) JSONObject.toJSON(quotasDataValueEntity);
            innerObjArr.add(quotasDataValueEntityJsonObject);
        }
        jsonObject.put("QuotasValueList",innerObjArr);
        String string = jsonObject.toString();
        return string;
    }
}
