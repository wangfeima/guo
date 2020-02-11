package BackUp;

import com.alibaba.fastjson.JSONObject;

import java.util.Set;

import static common.utils.date.DateUtils.dayDiff;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/11/28 19:51
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.vcloude
 * @description:
 */
public class CapitalExtract {
    /**
     * MDGEN_ENT0009
     * @param ojsonObject
     * @return
     */
    public static boolean CapitalExtract(JSONObject ojsonObject) {
        double altBeCapitalAmount=0.00;
        double altAfCapitalAmount=0.00;
        if (
                ojsonObject.getString("altItem").contains("注册资本变更") & (dayDiff(ojsonObject.getString("altDate")) <= 365 * 2)
            ){
            //altModeLocal定义要获取altBe或altAf中的股东的名称
            String altBe = ojsonObject.getString("altBe");
            String altAf = ojsonObject.getString("altAf");
            altBeCapitalAmount = Double.valueOf(altBe.split(":")[1]);
            altAfCapitalAmount = Double.valueOf(altAf.split(":")[1]);
        }
        if ((altAfCapitalAmount < altBeCapitalAmount)){
            return true;
        }else {
            return false;
        }
    }


}
