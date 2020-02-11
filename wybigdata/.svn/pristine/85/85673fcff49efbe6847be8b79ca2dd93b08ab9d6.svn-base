package com.dfwy.online.sparkstreamingtask.jsondemo;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao;
import common.utils.etlclean.EtlCleanRules;
import common.utils.sqlsessionfactoryutil.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.util.List;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/14 10:58
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.jsondemo
 * @description:
 */
public class JsonDemo {
    public static void main(String[] args) {
        String jsonStr="{\"list\":[{\"uuid\":\"wy222222\",\"reqid\":\"东方微银2018\",\"entName\":\"张王力战\"},{\"uuid\":\"wy123457\",\"reqid\":\"东方微银2019\",\"entName\":\"张王力战1\"},{\"uuid\":\"wy123459\",\"reqid\":\"东方微银2020\",\"entName\":\"张王力战2\"}]}";

        //将Json字符串转为Json对象
        JSONObject jsonObject = JSONObject.parseObject(jsonStr);
        //获取Json数组
        JSONArray list = jsonObject.getJSONArray("list");

        List<JsonDemoObject> jsonDemoObjects = list.toJavaList(JsonDemoObject.class);
        for (JsonDemoObject jsonDemoObject : jsonDemoObjects) {
            System.out.println(jsonDemoObject.getUuid());
            System.out.println(jsonDemoObject.getReqid());
            System.out.println(jsonDemoObject.getEntName());
            jsonDemoObject.setUuid(EtlCleanRules.deleteSpace(jsonDemoObject.getUuid()));
        }
         SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
       /* <mapper namespace="com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao">*/
         ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
         ETLCleanWriteDao.inserjsonDemoObjectList(jsonDemoObjects);

         sqlSession.commit();
         sqlSession.close();
    }
}
