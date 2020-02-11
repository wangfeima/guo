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
public class ShareholderExtract {
    /**
     * MDGEN_ENT0009
     * @param resultSet
     * @param ojsonObject
     * @param altMode  altAf  altBe
     * @return
     */
    public static Set<String> ShareholderExtract(Set<String> resultSet, JSONObject ojsonObject,String altMode) {
        if (
                (
                        ojsonObject.getString("altItem").contains("股东")
                                |ojsonObject.getString("altItem").contains("出资人")
                                |ojsonObject.getString("altItem").contains("股权")
                                |ojsonObject.getString("altItem").contains("投资人")
                )
                        &dayDiff(ojsonObject.getString("altDate")) <= 365 * 2){


            //altModeLocal定义要获取altBe或altAf中的股东的名称
            String altModeLocal = ojsonObject.getString(altMode);

            if(altModeLocal.contains("|")|altModeLocal.contains("认缴")){
                /*股东模式一：【altbe】=“张三认缴|李四认缴” 【altaf】=“张三认缴”*/
                /**
                 * 1、首先，判断是否存在"|",如果存在世界使用"|"进行分割成数组
                 * 2、对数组进行遍历，替换掉所有“认缴”获取股东名称
                 * 3、如果不存在"|"，则直接进行替换工作。
                 */
                if (altModeLocal.contains("|")) {
                    String[] split = altModeLocal.split("|");
                    for (String s : split) {
                        resultSet.add(s.replace("认缴", ""));
                    }
                }else {
                    resultSet.add(altModeLocal.replace("认缴", ""));
                }
            }else if(altModeLocal.contains("企业名称:")|altModeLocal.contains("名称:")){
                /*股东模式二：【altbe】=“姓名: 张三; 出资额: 20; 百分比: 2 企业名称: 李四; 出资额: 30;百分比: 3” 【altaf】=“企业名称: 李四; 出资额: 30;百分比: 3”*/
                /**
                 * 1、首先，替换掉所有空格符：姓名:张三;出资额:20;百分比:2企业名称:李四;出资额:30;百分比:3
                 * 2、其次，按";"进行分割成数组:["姓名:张三","出资额:20","百分比:2企业名称:李四","出资额:30","百分比:3"}
                 * 3、随后，遍历数据，对含有"姓名"或"企业名称"的元素以":"进行分割，如果长度大于2，则取坐标为2的元素值；如果小于等于2，则取坐标为1的元素值
                 *    ["姓名","张三"] ["百分比","2企业名称","李四"]
                 */
                if (altModeLocal.contains("姓名")|altModeLocal.contains("企业名称")){
                    altModeLocal= altModeLocal.replaceAll(" +","");
                }

                String[] split = altModeLocal.split(";");
                for (String s : split) {
                    if (s.contains("姓名")|s.contains("企业名称")){
                        final String[] split1 = s.split(":");
                        if (split1.length>2){
                            resultSet.add(split1[2]);
                        }else if(split1.length<=2){
                            resultSet.add(split1[1]);
                        }
                    }
                }
            }else if (altModeLocal.contains("法人股东:")|altModeLocal.contains("自然人股东:")){
                /*股东模式三："altAf": "股东名录: 法人股东:德正资源控股有限公司 4650  自然人股东:卢春雷 300 张晶先1 50",
							"altBe": "股东名录: 法人股东:德正资源控股有限公司 4950  自然人股东:张晶先 50"*/
                /**
                 * 1、首先，将不需要的字符串去掉结果变为：德正资源控股有限公司 4950  张晶先 50
                 * 2、去除字符串两端的空格符：德正资源控股有限公司 4950  张晶先 50
                 * 3、将包括2个以上的空格替换成一个空格：德正资源控股有限公司 4950 张晶先 50
                 * 4、以空格为分隔符进行分割成字符数组：["德正资源控股有限公司","4950","张晶先","50"]
                 * 5、获取股东姓名 由以上规律发现股东名称是数组的偶数。
                 */
                altModeLocal=altModeLocal.replace("股东名录:","")
                        .replace("法人股东:","")
                        .replace("自然人股东:","");
                altModeLocal=altModeLocal.trim();
                altModeLocal = altModeLocal.replaceAll(" {2,}", " ");
                String[] split = altModeLocal.split(" ");
                for(int i=0;i<split.length;i++){
                    if ((i&1) != 1){
                        resultSet.add(split[i]);
                    }
                }
            }else{
                resultSet.add("");
            }
        }
        return resultSet;
    }
}
