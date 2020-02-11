package BackUp;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import common.utils.stringutils.StringUtils;
import scala.annotation.meta.param;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static common.utils.date.DateUtils.*;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/11/26 9:35
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: vcloude01
 * @pachage_name: com.dfwy.online.sparkstreamingtask.vcloude
 * @description:
 */


public class JsonAnalysis01 {

/*     *注意没有报文格式，所以，没有测试！
     *MDGEN_ENT0001
     *最近2年内所受严重行政处罚次数*/

    public static Map<String, Object> MDGEN_ENT0001(String record, Map<String, Object> mapReCallBack) {
        JSONObject jsonObject = JSON.parseObject(record);
        JSONObject amarsoftData = jsonObject.getJSONObject("amarsoftData");
        JSONObject r1103 = amarsoftData.getJSONObject("R1103");
        JSONArray data = r1103.getJSONArray("data");
        JSONArray caseInfoList = data.getJSONObject(0).getJSONArray("caseInfoList");


        //严重行政处罚次数
        int NumberOfSeriousAdministrativePunishments = 0;
        for (Object o : caseInfoList) {
            String maxDate = "";
            JSONObject ojsonObject = (JSONObject) o;
            if ((StringUtils.isNotEmpty(ojsonObject.getString("caseTime"))
                    | StringUtils.isNotEmpty(ojsonObject.getString("pendecissDate")))
                    &
                    (ojsonObject.getString("penType").contains("吊销执照")
                            | ojsonObject.getString("penType").contains("吊销许可证")
                            | ojsonObject.getString("penType").contains("停产")
                            | ojsonObject.getString("penType").contains("停业"))
                    ) {
                if (StringUtils.isEmpty(ojsonObject.getString("caseTime"))) {
                    maxDate = ojsonObject.getString("pendecissDate");
                } else if (StringUtils.isEmpty(ojsonObject.getString("pendecissDate"))) {
                    maxDate = ojsonObject.getString("caseTime");
                } else if (getDateDiff_Minus_Positive(ojsonObject.getString("caseTime"), ojsonObject.getString("pendecissDate")) > 0) {
                    maxDate = ojsonObject.getString("caseTime");
                } else {
                    maxDate = ojsonObject.getString("pendecissDate");
                }
                if (dayDiff(maxDate) < 365 * 2) {
                    NumberOfSeriousAdministrativePunishments++;
                }
                ;
            }
        }
        mapReCallBack.put("MDGEN_ENT0001", NumberOfSeriousAdministrativePunishments);
        return mapReCallBack;
    }


    public static Map<String, Object> MDGEN_ENT0002_3_4(String record, Map<String, Object> mapReCallBack) {
        JSONObject jsonObject = JSON.parseObject(record);
        JSONObject amarsoftData = jsonObject.getJSONObject("amarsoftData");
        JSONObject r1103 = amarsoftData.getJSONObject("R1103");
        JSONArray data = r1103.getJSONArray("data");
        JSONArray basicList = data.getJSONObject(0).getJSONArray("basicList");
        JSONObject basicJSONObject = basicList.getJSONObject(0);

        //注册币种
        String regCapCur = basicJSONObject.getString("regCapCur");
        //注册资金
        double regCap = basicJSONObject.getDouble("regCap");
        //开业日期（YYYY-MM-DD）
        String esDate = basicJSONObject.getString("esDate");
        //企业（机构）类型
        String enterpriseType = basicJSONObject.getString("enterpriseType");

/*        MDGEN_ENT0002 注册资本
        if【注册币种】contains 人民币 or null then 【注册资本】=【注册资金】*10000
        else 【注册资本】=null*/


        if (regCapCur.isEmpty() || regCapCur.equals("人民币")) {
            regCap = regCap * 10000;
            mapReCallBack.put("MDGEN_ENT0002", regCap);
        } else {
            mapReCallBack.put("MDGEN_ENT0002", null);
        }
/*        MDGEN_ENT0003 登记注册类型
        if 【企业（机构）类型】 contains 股份有限公司 then 01
        else if 【企业（机构）类型】contains  有限责任公司  then 02
        else if 【企业（机构）类型】 contains 合伙企业  then 03
        else if 【企业（机构）类型】contains  个人独资企业  then 04
        else if 【企业（机构）类型】contains 个体 then 05
        else 06;*/


        if (enterpriseType.contains("股份有限公司")) {
            mapReCallBack.put("MDGEN_ENT0003", "01");
        } else if (enterpriseType.contains("有限责任公司")) {
            mapReCallBack.put("MDGEN_ENT0003", "02");
        } else if (enterpriseType.contains("合伙企业")) {
            mapReCallBack.put("MDGEN_ENT0003", "03");
        } else if (enterpriseType.contains("个人独资企业")) {
            mapReCallBack.put("MDGEN_ENT0003", "04");
        } else if (enterpriseType.contains("个体")) {
            mapReCallBack.put("MDGEN_ENT0003", "05");
        } else {
            mapReCallBack.put("MDGEN_ENT0003", "06");
        }
/*        MDGEN_ENT0004 经营年限
        (【申请日期】 - 【成立日期】)/365*/


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date es = null;
        try {
            es = format.parse(esDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long result = 0;
        result = (System.currentTimeMillis() - es.getTime()) / (24 * 60 * 60 * 1000) / 365;
        mapReCallBack.put("MDGEN_ENT0004", String.valueOf(result));
        return mapReCallBack;
    }

    /**
     * @param record        报文
     * @param mapReCallBack 返回的计算结果信息
     * @param applicant     贷款申请人
     * @return
     */


    public static Map<String, Object> MDGEN_ENT0005_15(String record, Map<String, Object> mapReCallBack, String applicant) {
        JSONObject jsonObject = JSON.parseObject(record);
        JSONObject amarsoftData = jsonObject.getJSONObject("amarsoftData");
        JSONObject r1103 = amarsoftData.getJSONObject("R1103");
        JSONArray data = r1103.getJSONArray("data");
        JSONArray shareHolderList = data.getJSONObject(0).getJSONArray("shareHolderList");


        double maxFundedRatio = 0.0;
        String outFunderdRatio = "";
        for (Object o : shareHolderList) {
/*            MDGEN_ENT0005
            最大股东股权占比
            max（【出资比例】）*/


            JSONObject ojsonObject = (JSONObject) o;
            String fundedRatioStr = ojsonObject.getString("fundedRatio");
            String replace = fundedRatioStr.replace("%", "");
            Double fundedRatio = Double.valueOf(replace);
            if (maxFundedRatio < fundedRatio) {
                maxFundedRatio = fundedRatio;
                outFunderdRatio = fundedRatioStr;
            }
/*            MDGEN_ENT0015
            申请人股权占比
            【出资比例】|【股东名称】=【申请人姓名】*/


            String shareholderName = ojsonObject.getString("shareholderName");
            if (StringUtils.isNotEmpty(applicant) & applicant.equals(shareholderName)) {
                mapReCallBack.put("MDGEN_ENT0015", ojsonObject.getString("fundedRatio"));
            }
        }
        mapReCallBack.put("MDGEN_ENT0005", outFunderdRatio);
        return mapReCallBack;
    }
    /*MDGEN_ENT0006
    两年内法定代表人或负责人变更次数
    MDGEN_ENT0007
    最近一次法定代表人或负责人变更距离当前的月数
    MDGEN_ENT0009
    两年内股东退出人次
    MDGEN_ENT0012
    两年内企业注册资本减少次数
    count（【变更事项】contains 注册资本& 【变更后内容】<【变更前内容】&（【申请日期】-【变更日期】）<=365*2
    MDGEN_ENT0013
    两年内企业住所变更次数
    count(（distinct【变更事项】&【变更日期】|【 变更事项】 contains（地址，住所）& （【申请日期】- 【变更日期】)/365<=2)
    MDGEN_ENT0014
    两年内经营范围变更次数
    count(distinct【变更事项】&【变更日期】|【 变更事项】 contains 经营范围  &（【申请日期】- 【变更日期】)/365<=2)*/


    public static Map<String, Object> MDGEN_ENT0006_7_9_12_13_14(String record, Map<String, Object> mapReCallBack) {
        JSONObject jsonObject = JSON.parseObject(record);
        JSONObject amarsoftData = jsonObject.getJSONObject("amarsoftData");
        JSONObject r1103 = amarsoftData.getJSONObject("R1103");
        JSONArray data = r1103.getJSONArray("data");
        JSONArray alterList = data.getJSONObject(0).getJSONArray("alterList");

        //法定代表人变更时间上的集合
        Set<String> jsonObjectSet = new HashSet<>();
        //变更月数
        double mouthsNumber = 0.00;
        //变更时间差
        double daysNumber = 0.00;
        //初始化赋值给时间差时标志位，100这个值并没有任何含义，仅仅是为了表明初次赋值
        int j = 100;
        //变更前集合
        Set<String> altBeShareholderSet = new HashSet<>();
        //变更后集合
        Set<String> altAfShareholderSet = new HashSet<>();
        //变更前和变更后减少数量
        int shareholderReductionNumber = 0;
        //变更前集合
        Set<String> altBeCapitalSet = new HashSet<>();
        //变更后集合
        Set<String> altAfCapitalSet = new HashSet<>();
        //变更前和变更后减少数量
        int capitalReductionNumber = 0;

        //企业地址变更时间上的集合
        Set<String> addressAltDateSet = new HashSet<>();
        Set<String> BusinessScopeAltDateSet = new HashSet<>();

        for (Object o : alterList) {
            JSONObject ojsonObject = (JSONObject) o;
            //MDGEN_ENT0006
            if ((dayDiff(ojsonObject.getString("altDate")) <= 365 * 13 & ojsonObject.getString("altItem").contains("负责人变更"))) {
                jsonObjectSet.add(ojsonObject.getString("altDate"));
            }
            //MDGEN_ENT0007
            if (ojsonObject.getString("altItem").contains("负责人变更") & ojsonObject.getString("altItem").contains("法定代表人")) {
                //初始化赋值给时间差
                if (j == 100) {
                    daysNumber = dayDiff(ojsonObject.getString("altDate"));
                    j = 200;
                }
                if (daysNumber >= dayDiff(ojsonObject.getString("altDate"))) {
                    daysNumber = dayDiff(ojsonObject.getString("altDate"));
                    mouthsNumber = dayDiff(ojsonObject.getString("altDate")) / (365 / 12);
                }
            }
            //MDGEN_ENT0009
            altBeShareholderSet = ShareholderExtract.ShareholderExtract(altBeShareholderSet, ojsonObject, "altBe");
            altAfShareholderSet = ShareholderExtract.ShareholderExtract(altAfShareholderSet, ojsonObject, "altAf");
            //MDGEN_ENT0012
            if (CapitalExtract.CapitalExtract(ojsonObject)) {
                capitalReductionNumber++;
            }
            //MDGEN_ENT0013
            if ((dayDiff(ojsonObject.getString("altDate")) <= 365 * 2 & ojsonObject.getString("altItem").contains("地址变更"))) {
                addressAltDateSet.add(ojsonObject.getString("altDate"));
            }
            //MDGEN_ENT0014
            if ((dayDiff(ojsonObject.getString("altDate")) <= 365 * 2 & ojsonObject.getString("altItem").contains("经营范围变更"))) {
                BusinessScopeAltDateSet.add(ojsonObject.getString("altDate"));
            }
        }
        //MDGEN_ENT0009
        for (String s : altBeShareholderSet) {
            if (!altAfShareholderSet.contains(s)) {
                shareholderReductionNumber++;
            }
        }

        mapReCallBack.put("MDGEN_ENT0006", jsonObjectSet.size());
        mapReCallBack.put("MDGEN_ENT0007", (int) mouthsNumber);
        mapReCallBack.put("MDGEN_ENT0009", shareholderReductionNumber);
        mapReCallBack.put("MDGEN_ENT0012", capitalReductionNumber);
        mapReCallBack.put("MDGEN_ENT0013", addressAltDateSet.size());
        mapReCallBack.put("MDGEN_ENT0014", BusinessScopeAltDateSet.size());

        altBeShareholderSet.clear();
        altAfShareholderSet.clear();
        alterList.clear();
        return mapReCallBack;
    }


    /**
     * MDGEN_ENT0008
     * 最近一次发生股权冻结距离现在的月数
     *
     * @param record
     * @param mapReCallBack
     * @return
     */


    public static Map<String, Object> MDGEN_ENT0008(String record, Map<String, Object> mapReCallBack) {
        JSONObject jsonObject = JSON.parseObject(record);
        JSONObject amarsoftData = jsonObject.getJSONObject("amarsoftData");
        JSONObject r1103 = amarsoftData.getJSONObject("R1103");
        JSONArray data = r1103.getJSONArray("data");
        JSONArray sharesFrostList = data.getJSONObject(0).getJSONArray("sharesFrostList");

        //months
        Set<Double> froMoths = new HashSet<Double>();
        //The lastest month
        Double lastestMonth = 0.0;
        //计算时间
        String froTime = "";
        for (Object o : sharesFrostList) {
            JSONObject ojsonObject = (JSONObject) o;
            if (StringUtils.isNotEmpty(ojsonObject.getString("froFrom"))
                    | StringUtils.isNotEmpty(ojsonObject.getString("froTo"))) {
                if (StringUtils.isEmpty(ojsonObject.getString("froTo"))) {
                    froTime = ojsonObject.getString("froFrom");
                } else if (true) {

                }
            }
        }


        //mapReCallBack.put("MDGEN_ENT0001",NumberOfSeriousAdministrativePunishments);
        return mapReCallBack;
    }
}
