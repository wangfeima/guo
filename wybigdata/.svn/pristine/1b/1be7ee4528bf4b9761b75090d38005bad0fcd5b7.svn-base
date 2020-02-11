package common.utils.etlclean;
import common.utils.stringutils.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/14 15:45
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: common.utils.etlclean
 * @description:原始数据向标准数据清洗规则
 */
public class EtlCleanRules {
    /*
    * @Description: 删除字符串两端空格
    * @param: [str]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/14 15:54
    */
    public static String deleteSpace(String string) {
        if (StringUtils.isNotEmpty(string)) {
            return StringUtils.trim(string);
        } else {
            return "";
        }
    }
    /*
    * @Description: 删除所有空格 （12）
    * @param: [string]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/20 13:26
    */
    public static String deleteAllSpace(String string){
       return StringUtils.extractBlank(string);
    }
    /*
    * @Description:
    * string为空，返回0.0;如果返回带%将百分制转double;如果不带%则直接转成double返回 (11)
    * 出现精确度异常是因为在String转double时出现的问题。所以，这里使用了BigDecimal提高了数据精度转换！
    * @param: [string, number]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/14 16:17
    */
    public static double deletePercentSign(String string)  {
        if (StringUtils.isNotEmpty(string)) {
            if (string.contains("%")) {
                String value = string.replaceAll("[^0-9.]+", "");
                BigDecimal temp = BigDecimal.valueOf(Double.valueOf(value));
                temp = temp.divide(BigDecimal.valueOf(100));
                double v = temp.doubleValue();
                return v;
            } else {
                return Double.parseDouble(string.replaceAll("[^0-9.]+", ""));
            }
        }
        return 0.00;
    }
    /*
    * @Description: 删除特殊字符 (18)
    * @param: [string, specialCharacters]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/14 16:23
    */
    public static String deleteSpecialCharacters(String string) {
        return StringUtils.retainTheSpecifiedCharacter(string);
    }

    /*
    * @Description: 删除数字前多余的0，比如001500变成1500 (17)
    * @param: [string]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/14 16:20
    */
    public static String deleteZero(String frontStr) {
        if (StringUtils.isNotEmpty(frontStr)){
            return  frontStr.replaceAll("^(0+)", "");
        }else {
            return "";
        }
    }
    /**
     * Wfm
     * 将"/"替换成"-"
     * @param strDir
     * @return
     */
    public static String changeDirectionh(String strDir) {
        String s = "/";
        String a = "-";
        if (strDir != null && !" ".equals(strDir)) {
            if (strDir.contains(s)) {
                strDir = strDir.replace(s, a);
            }
        }
        return strDir;
    }
    /**
     * Wfm
     * 将时间戳转换为时间
     * @param
     * @return
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }
    /*
    * @Description: 金额转换
    * @param: [amount]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/20 9:33
    */
    public static String amountConversion(String amount){
        if (StringUtils.isNotEmpty(amount)){
            return EtlCleanRules.deleteZero(EtlCleanRules.deleteSpecialCharacters(amount));
        }else {
            return "";
        }
    }
    /*String date = dateFormatCleaning("1989/09!@#^&*().01 09:10:00"  "19900101--->1990-01-01");
    * @Description: 日期格式的清洗(19)
    * @param: [strSource]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/20 12:09
    */
    public static String dateFormatCleaning(String strSource){
        String s1 = StringUtils.transformationDirection(strSource, "/", "-");
        String s2 = StringUtils.transformationDirection(s1, ".", "-");
        s2=StringUtils.stringFilter(s2);
        return s2;
    }

    /*
    * @Description: 金额删除逗号(9)
    * @param: [strSource]
    * @return: java.lang.Double
    * @author:Dxx
    * @Date: 2018/12/20 12:14
    */
    public static String amountDeleteComma(String strSource){
        String s1 = StringUtils.transformationDirection(strSource, ",", "");
        String s2 = StringUtils.transformationDirection(s1, "，", "");
        return s2;
    }
    /*
    * @Description: 删除+号 (16)
    * @param: [string]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/20 13:32
    */
    public static String deletePlus(String string){
        return StringUtils.isNotEmpty(string) ? string.replace("+",""):string;
    }
    /*
    * @Description: 删除-号 (15)
    * @param: [string]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/20 13:51
    */
    public static String deleteMinus(String string){
        return StringUtils.isNotEmpty(string) ? string.replace("-",""):string;
    }
    /*
    * @Description: 判断是否是整数，不是整数返回'0' (7)
    * @param: [string]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/20 14:11
    */
    public static String isNumeric(String string){
        return StringUtils.isNumeric(string)?string:"null";
    }
    /*
    * @Description: 清洗变更事项（22）
    * @param: [str]
    * @return: java.lang.String
    * @author:Yxx
    * @Date: 2018/12/27 11:37
    */
    public static String cleanAltitem(String str) {
        String matchedPattern = null;
        String strRegex1 = ".*(股东|出资人|股权|投资人).*";
        String strRegex2 = ".*(股权冻结|股权出质).*";
        if(StringUtils.isNotEmpty(str)){
            if(str.matches(strRegex1)){
                if(str.matches(strRegex2)){
                    matchedPattern="0";
                }else {
                    matchedPattern="21";
                }
            }else{
                matchedPattern="0";
            }
        }else {
            matchedPattern="0";
        }
        return matchedPattern;
    }
    /*
     * @Description: 删除百分比以外的特殊字符（21）
     *             去掉特殊字符(%除外)---result = value.replace(/[&\|\\\*^$#@']/g,"")
     * 跟这个区别开 去掉特殊字符         result = value.replace(/[&\|\\\*^%$#@']/g,"")
     * @param: [str]
     * @return: java.lang.String
     * @author:  RemoveSpecialCharacterPercentExcept
     * @Date: 2018/12/27 13:40
     */
    public static String removeSpecialCharacterExceptPercent(String str) {
        if (StringUtils.isNotEmpty(str)){
            //return str.replaceAll("[`~!@#$^&*()+=|{}':;',\\[\\].<>/?~！@#￥……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
            return str.replaceAll("[`~@#$^&*]", "");
        }else {
            return str;
        }
    }
    /*
     * @Description: 企业机构类型清洗（24）
     * @param: [str]
     * @return: java.lang.String
     * @author:
     * @Date: 2018/12/27 14:20
     */
    public static String enterOrgTypeClean(String str) {
        String matchedPattern = null;
        String strRegex1 = ".*(分支机构|分公司|办事处|代表机构).*";
        String strRegex2 = ".*(外商独资|(外国.*独资)).*";
        String strRegex3 = ".*(台.*独资).*";
        String strRegex4 = ".*(中外).*";
        String strRegex5 = ".*(个人独资).*";
        String strRegex6 = ".*(个体).*";
        String strRegex7 = ".*(股份有限公司|股份合作|股份制).*";
        String strRegex8 = ".*(一人有限责任公司|(有限责任公司.*独资)).*";
        String strRegex9 = ".*(有限责任公司).*";
        String strRegex10 = ".*(特殊普通合伙|有限合伙).*";
        String strRegex11 = ".*(合伙企业).*";
        String strRegex12 = ".*(农民专业合作).*";

        if (StringUtils.isNotEmpty(str)) {
            if (str.matches(strRegex1)) {
                matchedPattern = "01";
            } else if (str.matches(strRegex2)) {
                matchedPattern = "02";
            } else if (str.matches(strRegex3)) {
                matchedPattern = "03";
            } else if (str.matches(strRegex4)) {
                matchedPattern = "12";
            } else if (str.matches(strRegex5)) {
                matchedPattern = "04";
            } else if (str.matches(strRegex6)) {
                matchedPattern = "05";
            } else if (str.matches(strRegex7)) {
                matchedPattern = "06";
            } else if (str.matches(strRegex8)) {
                matchedPattern = "07";
            } else if (str.matches(strRegex9)) {
                matchedPattern = "08";
            } else if (str.matches(strRegex10)) {
                matchedPattern = "09";
            } else if (str.matches(strRegex11)) {
                matchedPattern = "10";
            } else if (str.matches(strRegex12)) {
                matchedPattern = "11";
            } else {
                matchedPattern = "99";
            }
        } else {
            matchedPattern = "99";
        }
        return matchedPattern;
    }
    /*
     * @Description: 登记状态清洗（25）
     * @param: [str]
     * @return: java.lang.String
     * @author:
     * @Date: 2018/12/27 14:20
     */
    public static String registStatusClean(String str) {
        String matchedPattern = null;
        String strRegex1 = ".*(在营|存续|存活|开业|正常|在册).*";
        String strRegex2 = ".*(吊销).*";
        String strRegex3 = ".*(注销|清算|停业).*";
        String strRegex4 = ".*(迁出).*";
        if (StringUtils.isNotEmpty(str)) {
            if (str.matches(strRegex1)) {
                matchedPattern = "01";
            } else if (str.matches(strRegex2)) {
                matchedPattern = "02";
            } else if (str.matches(strRegex3)) {
                matchedPattern = "03";
            } else if (str.matches(strRegex4)) {
                matchedPattern = "04";
            } else {
                matchedPattern = "99";
            }
        } else {
            matchedPattern = "99";
        }
        return matchedPattern;
    }
    /*
     * @Description: 空日期清洗(可以为null) （20）
     * @param: [str]
     * @return: java.lang.String
     * @author:
     * @Date: 2018/12/27 14:20
     */
    public static String isEmptyDateClean(String str) {
        if("".equals(str)){
            return null;
        }else {
            return str;
        }
    }
    public static void main(String[] args) throws ParseException {
        String str="nih%ao**77%66))777".replaceAll("[`~!@#$^&*()+=|{}':;',\\[\\].<>/?~！@#￥……& amp;*（）——+|{}【】‘；：”“’。，、？|-]", "");
        System.out.println(removeSpecialCharacterExceptPercent("nih%ao**77%,,./.;66))777"));
        /*System.out.println(deleteSpace(" .09 0% "));
        System.out.println(deleteAllSpace(" .09 0% "));
        System.out.println(deletePercentSign("0.020000031%"));
        System.out.println(deletePercentSign(" .0 @@@9 0"));
        System.out.println(deleteSpecialCharacters("1a阿姨……%……&*（——）————————（*&……%￥#￥%……&*（“？》”}：《》"));
        System.out.println(deleteZero("10000y1324以"));
        System.out.println(dateFormatCleaning("11989-12/12.22112"));
        System.out.println(dateFormatCleaning(""));
        System.out.println(amountDeleteComma("199,99，9099"));
        System.out.println(deleteMinus("+++++------789789789"));
        System.out.println(deletePlus("+++++------789789789"));
        System.out.println(isNumeric("jfldsajklf"));
        System.out.println(isNumeric("-12435678"));
        System.out.println(isNumeric("+12435.678"));
        String s = "19.90100011111";
        BigDecimal temp = BigDecimal.valueOf(Double.valueOf(s));
        // 将temp乘以100
        //temp = temp.multiply(BigDecimal.valueOf(100));
        temp = temp.divide(BigDecimal.valueOf(100));
        double sum = temp.doubleValue();
        System.out.println(sum);

        //stdEntMorGuainfoList.setQuan(morguaInfoListObject.getString("quan")); 12,15,16,18,17,7
        String quan = EtlCleanRules.deleteAllSpace("+222.400");
        String s4 = EtlCleanRules.deleteMinus(quan);
        String s1 = EtlCleanRules.deletePlus(s4);
        String s2 = EtlCleanRules.deleteSpecialCharacters(s1);
        String s3 = EtlCleanRules.deleteZero(s2);
        String numeric = EtlCleanRules.isNumeric(s3);
        System.out.println(numeric);

        System.out.println(dateFormatCleaning("1990!#$%^&/09.09%&*("));*/
    }
}
