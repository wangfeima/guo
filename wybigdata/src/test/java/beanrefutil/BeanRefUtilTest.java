package beanrefutil;

import common.pojo.applicantinformation.ApplicantInformationTO;
import common.utils.beanrefutil.BeanRefUtil;
import common.utils.stringutils.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/3 16:55
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: beanrefutil
 * @description:
 */
public class BeanRefUtilTest {
    public static void main(String[] args) {
        ApplicantInformationTO applicantInformationTO = new ApplicantInformationTO();
        Map<String,String> map = new HashMap<String, String>();


        //set值
        map.put("uuid","joe");
        map.put("businessID","123456");
        map.put("reqID","123456");
        map.put("applyAmount","11");
        BeanRefUtil.setFieldValue(applicantInformationTO, map);
        System.out.println(applicantInformationTO.getApplyAmount().getClass().getName());

        //get值
        Map<String, String> valueMap = BeanRefUtil.getFieldValueMap(applicantInformationTO);
        for (String s : valueMap.keySet()) {
            if(StringUtils.isNotEmpty(valueMap.get(s)))
            System.out.println(valueMap.get(s)+"   "+s);
        }

    }
}
