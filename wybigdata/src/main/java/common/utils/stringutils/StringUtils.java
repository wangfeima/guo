package common.utils.stringutils;

import com.mysql.jdbc.CachedResultSetMetaData;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class StringUtils {

    private static final BigDecimal ZERO = new BigDecimal(0);
    private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    /**
     * 过滤空NULL
     * @param o
     * @return
     */
    public static String FilterNull(Object o) {
        return o != null && !"null".equals(o.toString()) ? o.toString().trim() : "" ;
    }

    /**
     * 是否为空
     * @param o
     * @return
     */
    public static boolean isEmpty(Object o) {
        if (o == null) {
            return true;
        }
        if ("".equals(FilterNull(o.toString()))) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 是否不为空
     * @param o
     * @return
     */
    public static boolean isNotEmpty(Object o) {
        if (o == null) {
            return false;
        }
        if ("".equals(FilterNull(o.toString()))) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * 是否可转化为数字
     * @param o
     * @return
     */
    public static boolean isNum(Object o) {
        try {
            new BigDecimal(o.toString());
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 是否可转化为Long型数字
     * @param o
     * @return
     */
    public static boolean isLong(Object o) {
        try {
            new Long(o.toString());
            return true;
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 转化为Long型数字, 不可转化时返回0
     * @param o
     * @return
     */
    public static Long toLong(Object o) {
        if (isLong(o)) {
            return new Long(o.toString());
        } else {
            return 0L;
        }
    }

    /**
     * 转化为int型数字, 不可转化时返回0
     * @param o
     * @return
     */
    public static int toInt(Object o) {
        if (isNum(o)) {
            return new Integer(o.toString());
        } else {
            return 0;
        }
    }

    /**
     * 按字符从左截取固定长度字符串, 防止字符串超长, 默认截取50
     * @param o
     * @return
     */
    public static String holdmaxlength(Object o) {
        int maxlength = 50;
        if (o == null) {
            return "";
        }
        return subStringByByte(o, maxlength);
    }

    /**
     * 从左截取固定长度字符串, 防止字符串超长, maxlength为0时默认50
     * @param o
     * @return
     */
    public static String holdmaxlength(Object o, int maxlength) {
        maxlength = maxlength <= 0 ? 50 : maxlength;
        if (o == null) {
            return "";
        }
        return subStringByByte(o, maxlength);
    }

    /**
     * 按字节截取字符串
     * @param len
     * @return
     */
    private static String subStringByByte(Object o, int len) {
        if (o == null) {
            return "";
        }
        String str = o.toString();
        String result = null;
        if (str != null) {
            byte[] a = str.getBytes();
            if (a.length <= len) {
                result = str;
            } else if (len > 0) {
                result = new String(a, 0, len);
                int length = result.length();
                if (str.charAt(length - 1) != result.charAt(length - 1)) {
                    if (length < 2) {
                        result = null;
                    } else {
                        result = result.substring(0, length - 1);
                    }
                }
            }
        }
        return result;
    }

    /**
     * 逗号表达式_添加
     * @param commaexpress 原逗号表达式 如 A,B
     * @param newelement   新增元素 C
     * @return A,B,C
     */
    public static String comma_add(String commaexpress, String newelement) {
        return comma_rect(FilterNull(commaexpress) + "," + FilterNull(newelement));
    }

    /**
     * 逗号表达式_删除
     * @param commaexpress  原逗号表达式 如 A,B,C
     * @param delelement 删除元素 C,A
     * @return B
     */
    public static String comma_del(String commaexpress, String delelement) {
        if ((commaexpress == null) || (delelement == null) || (commaexpress.trim().equals(delelement.trim()))) {
            return "";
        }
        String[] deletelist = delelement.split(",");
        String result = commaexpress;
        for (String delstr : deletelist) {
            result = comma_delone(result, delstr);
        }
        return result;
    }

    /**
     * 逗号表达式_单一删除
     * @param commaexpress  原逗号表达式 如 A,B,C
     * @param delelement 删除元素 C
     * @return A,B
     */
    public static String comma_delone(String commaexpress, String delelement) {
        if ((commaexpress == null) || (delelement == null) || (commaexpress.trim().equals(delelement.trim()))) {
            return "";
        }
        String[] strlist = commaexpress.split(",");
        StringBuffer result = new StringBuffer();
        for (String str : strlist) {
            if ((!str.trim().equals(delelement.trim())) && (!"".equals(str.trim()))) {
                result.append(str.trim() + ",");
            }
        }
        return result.toString().substring(0, result.length() - 1 > 0 ? result.length() - 1 : 0);
    }

    /**
     * 逗号表达式_判断是否包含元素
     * @param commaexpress 逗号表达式 A,B,C
     * @param element C
     * @return true
     */
    public static boolean comma_contains(String commaexpress, String element) {
        boolean flag = false;
        commaexpress = FilterNull(commaexpress);
        element = FilterNull(element);
        if (!"".equals(commaexpress) && !"".equals(element)) {
            String[] strlist = commaexpress.split(",");
            for (String str : strlist) {
                if (str.trim().equals(element.trim())) {
                    flag = true;
                    break;
                }
            }
        }
        return flag;
    }

    /**
     * 逗号表达式_取交集
     * @param commaexpressA 逗号表达式1  A,B,C
     * @param commaexpressB 逗号表达式2  B,C,D
     * @return B,C
     */
    public static String comma_intersect(String commaexpressA, String commaexpressB) {
        commaexpressA = FilterNull(commaexpressA);
        commaexpressB = FilterNull(commaexpressB);
        StringBuffer result = new StringBuffer();
        String[] strlistA = commaexpressA.split(",");
        String[] strlistB = commaexpressB.split(",");
        for (String boA : strlistA) {
            for (String boB : strlistB) {
                if (boA.trim().equals(boB.trim())) {
                    result.append(boA.trim() + ",");
                }
            }
        }
        return comma_rect(result.toString());
    }

    /**
     * 逗号表达式_规范
     * @param commaexpress  逗号表达式  ,A,B,B,,C
     * @return A,B,C
     */
    public static String comma_rect(String commaexpress) {
        commaexpress = FilterNull(commaexpress);
        String[] strlist = commaexpress.split(",");
        StringBuffer result = new StringBuffer();
        for (String str : strlist) {
            if (!("".equals(str.trim())) && !("," + result.toString() + ",").contains("," + str + ",") && !"null".equals(str)) {
                result.append(str.trim() + ",");
            }
        }
        return result.toString().substring(0, (result.length() - 1 > 0) ? result.length() - 1 : 0);
    }

    /**
     * 逗号表达式_反转
     * @param commaexpress A,B,C
     * @return C,B,A
     */
    public static String comma_reverse(String commaexpress) {
        commaexpress = FilterNull(commaexpress);
        String[] ids = commaexpress.split(",");
        StringBuffer str = new StringBuffer();
        for (int i = ids.length - 1; i >= 0; i--) {
            str.append(ids[i] + ",");
        }
        return comma_rect(str.toString());
    }

    /**
     * 逗号表达式_获取首对象
     * @param commaexpress A,B,C
     * @return A
     */
    public static String comma_first(String commaexpress) {
        commaexpress = FilterNull(commaexpress);
        String[] ids = commaexpress.split(",");
        System.out.println("length:" + ids.length);
        if ((ids != null) && (ids.length > 0)) {
            return ids[0];
        }
        return null;
    }

    /**
     * 逗号表达式_获取尾对象
     * @param commaexpress A,B,C
     * @return C
     */
    public static String comma_last(String commaexpress) {
        commaexpress = FilterNull(commaexpress);
        String[] ids = commaexpress.split(",");
        if ((ids != null) && (ids.length > 0)) {
            return ids[(ids.length - 1)];
        }
        return null;
    }

    /**
     * 替换字符串,支持字符串为空的情形
     * @param strData
     * @param regex
     * @param replacement
     * @return
     */
    public static String replace(String strData, String regex, String replacement) {
        return strData == null ? "" : strData.replaceAll(regex, replacement);
    }

    /**
     * 字符串转为HTML显示字符
     * @param strData
     * @return
     */
    public static String String2HTML(String strData){
        if( strData == null || "".equals(strData) ){
            return "" ;
        }
        strData = replace(strData, "&", "&amp;");
        strData = replace(strData, "<", "&lt;");
        strData = replace(strData, ">", "&gt;");
        strData = replace(strData, "\"", "&quot;");
        return strData;
    }

    /**     * 把异常信息转换成字符串，以方便保存 */
    public static String getexceptionInfo(Exception e){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            e.printStackTrace(new PrintStream(baos));
        }finally{
            try {
                baos.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        return baos.toString();
    }

    /** 过滤特殊符号 */
    public static String regex(String str){
        Pattern pattern = Pattern.compile("[0-9-:/ ]");// 中文汉字编码区间
        Matcher matcher;
        char[] array = str.toCharArray();
        for (int i = 0; i < array.length; i++) {
            matcher = pattern.matcher(String.valueOf(array[i]));
            if (!matcher.matches()) {// 空格暂不替换
                str = str.replace(String.valueOf(array[i]), "");// 特殊字符用空字符串替换
            }
        }
        return str;
    }
    /*
    * @Description: 保留指定字符，即在正则表达式中写入想保留的字符即可不被替换！
    * @param: [string]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/14 17:34
    */
    public static String retainTheSpecifiedCharacter(String string){
        if(isNotEmpty(string)){
            return string.replaceAll("[^0-9a-zA-Z\u4e00-\u9fa5.，,。？！“”\\s]+", "");
        }else{
            return "";
        }
    }

    /*
    * @Description: 删除正则表达式中的所有特殊字符
    * @param: [str]
    * @return: java.lang.String
    * @author:Dxx
    * @Date: 2018/12/14 17:36
    */
    public static String stringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字 // String regEx ="[^a-zA-Z0-9]";
        // 清除掉所有特殊字符
        //String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？_]";
        if(isNotEmpty(str)){
            String regEx="[`~!@#$%^&*()+=//\\\\\\|]";
            Pattern p = Pattern.compile(regEx);
            Matcher m = p.matcher(str);
            return m.replaceAll("").trim();
        }else {
            return "";
        }
    }



  /*
  * @Description: 删除百分比以外的特殊字符
  *             去掉特殊字符(%除外)---result = value.replace(/[&\|\\\*^$#@']/g,"")
  * 跟这个区别开 去掉特殊字符         result = value.replace(/[&\|\\\*^%$#@']/g,"")
  * @param: [str]
  * @return: java.lang.String
  * @author:  RemoveSpecialCharacterPercentExcept
  * @Date: 2018/12/27 13:40
  */
    public static String removeSpecialCharacterExceptPercent(String str) {





        return str;
    }





    /*
    * @Description:0.0231%-->就会转错2.3099999999999998E-4  对于数据要求比较高的数据就不要使用了
    * @param: [string]
    * @return: double
    * @author:Dxx
    * @Date: 2018/12/14 18:08
    */
    public static double stringToDouble(String string) throws ParseException {
        NumberFormat nf=NumberFormat.getPercentInstance();
        //Double result= (Double) nf.parse(string);
        Double result= nf.parse(string).doubleValue();
        return result;
    }


    public static String comma_insert(String commaexpress, String newelement,int index){
        int length = commaexpress.length();
        if ( index > length ) {
            index = length;
        }else if ( index < 0){
            index = 0;
        }
        String result = commaexpress.substring(0, index) + newelement + commaexpress.substring(index, commaexpress.length());
        return result;
    }

    /**
     * 将"/"替换成"\"
     * @param strDir
     * @return
     */
    public static String changeDirection(String strDir) {
        String s = "/";
        String a = "\\";
        if (strDir != null && !" ".equals(strDir)) {
            if (strDir.contains(s)) {
                strDir = strDir.replace(s, a);
            }
        }
        return strDir;
    }

    /**
     * @param strSource
     * @return
     */
    public static String transformationDirection(String strSource,String strDirect,String features) {
        if (strSource != null && !"".equals(strSource)) {
            if (strSource.contains(strDirect)) {
                strSource = strSource.replace(strDirect, features);
            }
        }
        return strSource;
    }

    /**
     * 去除字符串中 头和尾的空格，中间的空格保留
     *
     * @Title: trim
     * @Description:
     * @return String
     * @throws
     */
    public static String trim(String s) {
        int i = s.length();// 字符串最后一个字符的位置
        int j = 0;// 字符串第一个字符
        int k = 0;// 中间变量
        char[] arrayOfChar = s.toCharArray();// 将字符串转换成字符数组
        while ((j < i) && (arrayOfChar[(k + j)] <= ' '))
            ++j;// 确定字符串前面的空格数
        while ((j < i) && (arrayOfChar[(k + i - 1)] <= ' '))
            --i;// 确定字符串后面的空格数
        return (((j > 0) || (i < s.length())) ? s.substring(j, i) : s);// 返回去除空格后的字符串
    }
    /**
     * 得到大括号中的内容
     * @param str
     * @return
     */
    public static String getBrackets(String str) {
        int a = str.indexOf("{");
        int c = str.indexOf("}");
        if (a >= 0 && c >= 0 & c > a) {
            return (str.substring(a + 1, c));
        } else {
            return str;
        }
    }

    /**
     * 将字符串中所有的，替换成|
     *
     * @param str
     * @return
     */
    public static String commaToVerti(String str) {
        if (str != null && !"".equals(str) && str.contains(",")) {
            return str.replaceAll(",", "|");
        } else {
            return str;
        }
    }

    /**
     * 去掉字符串中、前、后的空格
     * @throws IOException
     */
    public static String extractBlank(String name) {
        if (name != null && !"".equals(name)) {
            return name.replaceAll(" +", "");
        } else {
            return name;
        }
    }

    /**
     * 将null换成""
     * @param str
     * @return
     */
    public static String ConvertStr(String str) {
        return str != null && !"null".equals(str) ? str.trim() : "";
    }

    /**
     * Description: obj转String
     * @Author: YanTianLin
     * @date: 2018年9月13日 上午10:49:56
     */
    public static String objToStr(Object obj){
        String objstr = String.valueOf(obj);
        if("null".equals(objstr) || "NULL".equals(objstr)){
            return "";
        }
        return objstr.trim();
    }


    /**
     * Description: obj转BigDecimal
     * @Author: YanTianLin
     * @date: 2018年9月19日 下午7:59:29
     */
    public static BigDecimal StringToBig(Object strobj, boolean isReturnNull){
        String str = String.valueOf(strobj);
        if(null == str || "".equals(str) || "null".equals(str) || "NULL".equals(str)){
            if(isReturnNull){
                return null;
            }else{
                str = "0";
            }
        }
        try{
            return new BigDecimal(str.trim()).setScale(8,BigDecimal.ROUND_HALF_DOWN);
        }catch(Exception e){
        }
        return new BigDecimal("0");
    }


    /**
     * Description: 得分范围控制
     * @Author: YanTianLin
     * @date: 2018年3月12日 上午10:48:43
     */
    public static BigDecimal scoreControl(BigDecimal vscore) {
        if (vscore.compareTo(ZERO) < 0) {
            vscore = ZERO;
        } else if (vscore.compareTo(ONE_HUNDRED) > 0) {
            vscore = ONE_HUNDRED;
        } else if (vscore.compareTo(ZERO) == 0 || vscore == null) {
            vscore = ZERO;
        }
        return vscore;
    }


    /*
    * @Description:判断String是否均是数字
    * @param: [str]
    * @return: boolean
    * @author:Dxx
    * @Date: 2018/12/20 14:05
    */
    public static boolean isNumeric(String str){
        Pattern pattern = Pattern.compile("[0-9-+.%]*");
        return isNotEmpty(str) ?pattern.matcher(str).matches() : false;
    }



    public static void main(String[] args){
        System.out.println(isNum("a"));
        System.out.println(isNum("-1"));
        System.out.println(isNum("01"));
        System.out.println(isNum("1E3"));
        System.out.println(isNum("1.a"));
        System.out.println(isLong("014650"));
        System.out.println(Long.parseLong("014650"));
        System.out.println(isNumeric(""));
    }
}