package com.dfwy.online.sparkstreamingtask.vcloude.task.sfanalysis;

import com.dfwy.online.sparkstreamingtask.dao.JudgeAnynasisReadDao;
import common.pojo.legalbean.LegalDataAdded;
import common.pojo.legalbean.LegalStructuredData;
import common.utils.collection.CollectionUtils;
import common.utils.exception.ServiceException;
import common.utils.sfanalysisutils.CNNMFilter;
import common.utils.sfanalysisutils.CommonUtil;
import common.utils.stringutils.StringUtils;
import common.utils.uuid.UUIDGenerator;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description:司法解析引擎【司法诉讼新增字段解析入库】
 * @author:Yxx
 * @Date: 2018/12/5 15:02
 * @copyright: 东方微银科技（北京）有限公司
 */
public class JudicialAnalysisImpl {

    private static Logger logger = Logger.getLogger(JudicialAnalysisImpl.class);
    private static final String ANYNASIS_BLANK = null;// 文档描述的”空“，暂定null
    private static Map<String, String> REGEXMAP_SAJE = new HashMap<String, String>(); // 涉案金额
    private static Map<String, String> REGEXMAP_SLJG = new HashMap<String, String>(); // 审理结果
    private static Map<String, String> REGEXMAP_FYDJ = new HashMap<String, String>(); // 法院等级
    private static Map<String, String> REGEXMAP_ROLE = new HashMap<String, String>(); // 客户角色准确性校验
    private static String SAJE_TARGET = null;// 涉案金额-金额基础正则
    private static String SAJE_UNIT = null;// 涉案金额-金额单位基础正则

    /**
     * @Description: 初始化正则Map(此方法随系统启动执行一次)
     * @param: [judgemapper]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/4 10:20
     */
    public static void init_regex(JudgeAnynasisReadDao judgemapper) throws IOException {

        // 1,REGEXMAP_SAJE 涉案金额
        List<Map<String, Object>> basicList_saje = judgemapper.caseMoneyBasic();// 正则基础表
        List<Map<String, Object>> regexList_saje = judgemapper.caseMoneyRegex();// 正则式表
        String name = "";
        String value = "";
        for (Map<String, Object> singleMap : regexList_saje) {
            name = (String) singleMap.get("NAME");
            value = (String) singleMap.get("VALUE").toString().replaceAll("\\s*", "");
            REGEXMAP_SAJE.put(name, initRegex(value, basicList_saje));

        }

        for (Map<String, Object> map : basicList_saje) {
            if ("target".equals((String) map.get("PARAM_NAME"))) {
                SAJE_TARGET = (String) map.get("VALUE");// (?:\d*[,，]*)*\d*\.?\d*[零一二三四五六七八九十百千万亿]*
                continue;
            }

            if ("unit".equals((String) map.get("PARAM_NAME"))) {
                SAJE_UNIT = (String) map.get("VALUE");
            }
        }

        // 2,审理结果
        List<Map<String, Object>> basicList_sljg = judgemapper.judgeResultBasic();// 正则基础表
        List<Map<String, Object>> regexList_sljg = judgemapper.judgeResultRegex();// 正则式表
        for (Map<String, Object> singleMap : regexList_sljg) {
            name = (String) singleMap.get("NAME");
            value = (String) singleMap.get("VALUE").toString().replaceAll("\\s*", "");
            REGEXMAP_SLJG.put(name, initRegex(value, basicList_sljg));
        }

        // 3,客户角色准确性校验
        List<Map<String, Object>> listRoleBasic = judgemapper.judgeRoleCheckBasic();
        List<Map<String, String>> listRoleRegex = judgemapper.judgeRoleCheckRegex();
        for (Map<String, String> singleMap : listRoleRegex) {
            name = singleMap.get("NAME");
            value = singleMap.get("REGEX").toString().replaceAll("\\s*", "");
            REGEXMAP_ROLE.put(name, initRegex(value, listRoleBasic));
        }

        //4，法院等级
        List<Map<String, Object>> basicList_fydj = judgemapper.courtLevelBasic();// 正则基础表
        List<Map<String, Object>> regexList_fydj = judgemapper.courtLevelRegex();// 正则式表
        for (Map<String, Object> singleMap : regexList_fydj) {
            name = (String) singleMap.get("NAME");
            value = (String) singleMap.get("VALUE").toString().replaceAll("\\s*", "");
            REGEXMAP_FYDJ.put(name, initRegex(value, basicList_fydj));
        }

    }

    /**
     * @Description: 实例化正则表达式，将对应的配置取值放入正则表达式
     * @param: [regex:需要被初始化的正则表达式 basicList:基础配置表]
     * @return: String
     * @author:Yxx
     * @Date: 2018/12/4 10:50
     */
    private static String initRegex(String regex, List<Map<String, Object>> basicList) {
        if (CollectionUtils.isEmpty(basicList)) {
            return regex;
        }

        String beReplace = "";
        String toReplace = "";
        for (Map<String, Object> map : basicList) {
            beReplace = "【" + (String) map.get("DESCRIPTION") + "】";
            if (regex.contains(beReplace)) {
                toReplace = (String) map.get("VALUE");
                regex = regex.replace(beReplace, "(?:" + toReplace + ")");// 非获取捕获
            }
        }
        return regex;
    }

    /**
     * @Description: 异步处理司法诉讼新增字段解析入库--改为同步解析入库
     * @param: [legalStructuredData reqID businessID]
     * @return: List<LegalDataAdded>
     * @author:Yxx
     * @Date: 2018/11/26 9:20
     */
    public List<LegalDataAdded> anynasisAndInsert(LegalStructuredData legalStructuredData, String reqID, String businessID, String dataDatasourceFrom) throws IOException {
        List<LegalDataAdded> list = new ArrayList<>();
        //1,案由分类 【参数：SerialNo:序列号，PType：公告类型， CaseNo：案号，CaseReason：案由】
        LegalDataAdded caseReason = getCaseReason(legalStructuredData, reqID,businessID);
        // 2,涉案金额【参数：SerialNo:序列号，EntName：企业名称，Plaintiff：原告，Party：被告，JudgementResult：判决结果】
        LegalDataAdded involvedMoney = getInvolvedMoneyorUnity(legalStructuredData, "SAJE", reqID,businessID);
        // 3，涉案金额单位【参数：SerialNo:序列号，EntName：企业名称，Plaintiff：原告，Party：被告，JudgementResult：判决结果】
        LegalDataAdded involvedMoneyUinty = getInvolvedMoneyorUnity(legalStructuredData, "SAJEDW", reqID,businessID);
        // 4，审理结果【参数：SerialNo:序列号，EntName：企业名称，Plaintiff：原告，Party：被告，JudgementResult：判决结果】
        LegalDataAdded judgementResult = getJudgementResult(legalStructuredData, reqID,businessID);
        // 5，审理程序【参数：SerialNo:序列号，CaseNo：案号】
        LegalDataAdded judgementProcedure = getJudgementProcedure(legalStructuredData, reqID, businessID);
        // 6，法院等级【参数：SerialNo:序列号，Court：公告法院】
        LegalDataAdded courtLevel = getCourtLevel(legalStructuredData, reqID, businessID);
        // 7，案件影响力【参数：SerialNo:序列号，CourtLevel：已解析的新增字段-法院等级，Phase：已解析的新增字段-审理程序】
        LegalDataAdded caseInfluence = getCaseInfluence(legalStructuredData, courtLevel, judgementProcedure, reqID, businessID);
        // 8，案件结果对客户的影响【参数：SerialNo:序列号，EntName：企业名称，Plaintiff：原告/上诉人/申请人，Party：被告/被上诉人/被申请人，SentenceBrief：已解析新增字段-审理结果】
        LegalDataAdded impactCaseResultOnCustomer = getImpactCaseResultOnCustomer(legalStructuredData, judgementResult, reqID,businessID);
        // 9，客户诉讼角色准确性校验【参数：serialNo序列号，entName企业名称】
        LegalDataAdded customerRoleCheck = getCustomerRoleCheck(legalStructuredData, reqID,businessID);

        //添加主键和数据来源
        caseReason.setID(UUIDGenerator.generate());
        caseReason.setDatasourcefrom(dataDatasourceFrom);

        involvedMoney.setID(UUIDGenerator.generate());
        involvedMoney.setDatasourcefrom(dataDatasourceFrom);

        involvedMoneyUinty.setID(UUIDGenerator.generate());
        involvedMoneyUinty.setDatasourcefrom(dataDatasourceFrom);

        judgementResult.setID(UUIDGenerator.generate());
        judgementResult.setDatasourcefrom(dataDatasourceFrom);

        judgementProcedure.setID(UUIDGenerator.generate());
        judgementProcedure.setDatasourcefrom(dataDatasourceFrom);

        courtLevel.setID(UUIDGenerator.generate());
        courtLevel.setDatasourcefrom(dataDatasourceFrom);

        caseInfluence.setID(UUIDGenerator.generate());
        caseInfluence.setDatasourcefrom(dataDatasourceFrom);

        impactCaseResultOnCustomer.setID(UUIDGenerator.generate());
        impactCaseResultOnCustomer.setDatasourcefrom(dataDatasourceFrom);

        customerRoleCheck.setID(UUIDGenerator.generate());
        customerRoleCheck.setDatasourcefrom(dataDatasourceFrom);

        // 返回记录
        list.add(caseReason);
        list.add(involvedMoney);
        list.add(involvedMoneyUinty);
        list.add(judgementResult);
        list.add(judgementProcedure);
        list.add(courtLevel);
        list.add(caseInfluence);
        list.add(impactCaseResultOnCustomer);
        list.add(customerRoleCheck);
        return list;
    }

    /**
     * @Description: 9、客户诉讼角色准确性校验
     * @param: [judgeData, reqID, businessID]
     * @return: LegalDataAdded
     * @author:Yxx
     * @Date: 2018/11/30 19:30
     */
    private LegalDataAdded getCustomerRoleCheck(LegalStructuredData judgeData, String reqID, String businessID) {
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno() == null ? "" : judgeData.getSerialno();// 序列号

        // 注意：正则匹配使用清洗了的数据
        String PlaintiffWithoutKuoHao = clearContent(judgeData.getPlaintiff());// 原告:清除各种括号及括号内的内容,以及例外正则式
        String PartyWithoutKuoHao = clearContent(judgeData.getParty());// 被告:清除各种括号及括号内的内容
        String EntName = judgeData.getEntname() == null ? "" : judgeData.getEntname();// 企业名称
        String EntNameWithoutKuoHao = clearContent(EntName);// 企业名称:清除各种括号的EntName//正则运算中，与清洗了结构一致，不带括号等等
        String Pdesc = judgeData.getPdesc() == null ? "" : judgeData.getPdesc();// 公告内容
        String PdescWithoutKuoHao = clearContent(Pdesc);// 清除各种括号的公告内容
        String Importstaff = clearContent(judgeData.getImportstaff());// 重要第三方
        String LawStatus = clearContent(judgeData.getLawstatus());//新增新需求 诉讼地位


        LegalDataAdded J9_KHSSJSZQXJY = new LegalDataAdded();
        try {
            SetParamCommon(J9_KHSSJSZQXJY, Serialno, "rolesVerify", "客户诉讼角色准确性校验", EntName, reqID,businessID);// 设置7项数据
            String[] khssjszqxjyArr = {"14", "15", "16", "17", "18", "22"};
            if (CommonUtil.equals(khssjszqxjyArr, Ptype)) {

                if (CommonUtil.hasChar(Pdesc)) {
                    // 三类正则式列表
                    String regex_BG[] = null;// 被告正则式
                    String regex_YG[] = null;// 原告正则式
                    String regex_ZYDSF[] = null;// 重要第三方正则式
                    if ("16".equals(Ptype)) {
                        regex_BG = new String[]{"r10"};
                        regex_YG = new String[]{"r20"};
                        regex_ZYDSF = new String[]{"r30"};

                    } else if (CommonUtil.equals(new String[]{"15", "18"}, Ptype)) {
                        regex_BG = new String[]{"r11", "r12", "r13", "r14"};
                        regex_YG = new String[]{"r21", "r22"};
                        regex_ZYDSF = new String[]{"r30"};

                    } else if (CommonUtil.equals(new String[]{"14", "17", "22"}, Ptype)) {
                        regex_BG = new String[]{"r11"};
                        regex_YG = new String[]{"r21"};
                        regex_ZYDSF = new String[]{"r30"};
                    }
                    /*** 规则开始 ****/

                    if (!(LawStatus.contains("第三") || LawStatus.contains("案外人"))) {

                        int count = inStrCount(EntNameWithoutKuoHao,
                                new String[]{PlaintiffWithoutKuoHao, PartyWithoutKuoHao, Importstaff, LawStatus});
                        if (count == 0) {
                            J9_KHSSJSZQXJY.setLegalvalue("0");
                        } else if (count >= 2) {
                            J9_KHSSJSZQXJY.setLegalvalue("3");
                        } else if (CommonUtil.inStr(EntNameWithoutKuoHao, PartyWithoutKuoHao) && regex_BG != null) {// 被告包含企业名称
                            for (String BG : regex_BG) {
                                String regex = REGEXMAP_ROLE.get(BG).replace("【企业名称】", EntNameWithoutKuoHao);
                                if (Pattern.matches(regex, PdescWithoutKuoHao)) {

                                    J9_KHSSJSZQXJY.setLegalvalue("0");
                                    break;
                                } else {
                                    J9_KHSSJSZQXJY.setLegalvalue("1");
                                }
                            }
                        } else if (CommonUtil.inStr(EntNameWithoutKuoHao, PlaintiffWithoutKuoHao) && regex_YG != null) {// 原告包含企业名称
                            for (String YG : regex_YG) {
                                String regex = REGEXMAP_ROLE.get(YG).replace("【企业名称】", EntNameWithoutKuoHao);
                                if (Pattern.matches(regex, PdescWithoutKuoHao)) {
                                    J9_KHSSJSZQXJY.setLegalvalue("0");
                                    break;
                                } else {
                                    J9_KHSSJSZQXJY.setLegalvalue("1");
                                }
                            }
                        } else if (CommonUtil.inStr(EntNameWithoutKuoHao, Importstaff) && regex_ZYDSF != null) {// 重要第三方包含企业名称
                            for (String ZYDSF : regex_ZYDSF) {
                                String regex = REGEXMAP_ROLE.get(ZYDSF).replace("【企业名称】", EntNameWithoutKuoHao);
                                if (Pattern.matches(regex, PdescWithoutKuoHao)) {

                                    J9_KHSSJSZQXJY.setLegalvalue("0");
                                    break;
                                } else {
                                    J9_KHSSJSZQXJY.setLegalvalue("1");
                                }
                            }
                        }
                    } else {
                        //legalvalue
                        J9_KHSSJSZQXJY.setLegalvalue("4");
                    }
                    /*** 规则结束 ****/
                    // 匹配对应的valueLabel和needingVerify
                    String value = J9_KHSSJSZQXJY.getLegalvalue();
                    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                    //通过Reources来获取我们的配置文件
                    InputStream resourceAsStream = Resources.getResourceAsStream("sqlmapconfig.xml");
                    SqlSessionFactory sqlSessionFactory = builder.build(resourceAsStream);
                    SqlSession sqlSession = sqlSessionFactory.openSession();
                    JudgeAnynasisReadDao mapper = sqlSession.getMapper(JudgeAnynasisReadDao.class);
                    String valueLabel = mapper.judgeRoleCheckMapping(value);

                    J9_KHSSJSZQXJY.setValuelabel(valueLabel);
                    if ("0".equals(value) || "4".equals(value)) {
                        J9_KHSSJSZQXJY.setFlag("0");
                    } else {
                        J9_KHSSJSZQXJY.setFlag("1");
                    }
                    //sqlSession.commit();
                    sqlSession.close();
                } else {
                    SetParamSpecial(J9_KHSSJSZQXJY, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                }

            } else {
                J9_KHSSJSZQXJY.setLegalvalue(ANYNASIS_BLANK);
                J9_KHSSJSZQXJY.setValuelabel(ANYNASIS_BLANK);
                J9_KHSSJSZQXJY.setFlag(ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            throw new ServiceException("客户诉讼角色准确性校验");
            //e.printStackTrace();
        }
        return J9_KHSSJSZQXJY;
    }

    /**
     * @Description: 设置每种新增字段各自的值（非统一）
     * @param: [legalDataAdded value valuelabel flag]
     * @return: void
     * @author:Yxx
     * @Date: 2018/11/26 10:45
     */
    private static void SetParamSpecial(LegalDataAdded legalDataAdded, String value, String valuelabel, String flag) {
        legalDataAdded.setLegalvalue(value);
        legalDataAdded.setValuelabel(valuelabel);
        legalDataAdded.setFlag(flag);
    }

    /**
     * @Description: Sor数组中含有test字段的对象个数
     * @param: [test sor]
     * @return: int
     * @author:Yxx
     * @Date: 2018/11/26 12:10
     */
    private static int inStrCount(String test, String[] sor) {
        int count = 0;
        if (StringUtils.isEmpty(test) || StringUtils.isEmpty(sor)) {
            return count;
        }

        for (String ss : sor) {
            if (ss.contains(test)) {
                count++;
            }
        }
        return count;
    }

    /**
     * @Description:涉案金额：内容替换
     * @param: [result]
     * @return: String
     * @author:Yxx
     * @Date: 2018/11/26 13:30
     */
    private static String clearContent(String result) {
        if (result == null || result == "null") {
            return "";
        }

        if (!CommonUtil.hasChar(result)) {
            return "";
        }

        // 去空白
        // 匹配任何空白字符，包括空格、制表符、换页符等。与 [\f\n\r\t\v] 等效。
        result = result.replaceAll("\\s", "");

        List<char[]> list = new ArrayList<char[]>() {
            private static final long serialVersionUID = 1L;

            {
                // 英文
                add(new char[]{'(', ')'});
                add(new char[]{'[', ']'});
                // add(new char[]{'{','}'});//防止清除必要内容
                // add(new char[]{'<','>'});//防止是大于小于号
                // 中文
                add(new char[]{'（', '）'});
                add(new char[]{'【', '】'});
                // add(new char[]{'{','}'});//防止清除必要内容
                add(new char[]{'《', '》'});
            }
        };

        for (char[] cs : list) {
            String regex = "\\char1[^\\char1\\char2]*\\char2";
            regex = regex.replaceAll("char1", String.valueOf(cs[0]));
            regex = regex.replaceAll("char2", String.valueOf(cs[1]));

            // 循环清除对应匹配项
            while (Pattern.compile(regex).matcher(result).find()) {
                result = result.replaceAll(regex, "");
            }
        }
        return result;
    }

    /**
     * @Description: 8，案件结果对客户的影响
     * @param: [judgeData, J4_SLJG, reqID，businessID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/5 9:48
     */
    private LegalDataAdded getImpactCaseResultOnCustomer(LegalStructuredData judgeData, LegalDataAdded J4_SLJG, String reqID, String businessID) {
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno() == null ? "" : judgeData.getSerialno().trim();// 序列号

        // 注意：正则匹配使用清洗了的数据
        String PlaintiffWithoutKuoHao = clearContent(judgeData.getPlaintiff());// 原告:清除各种括号及括号内的内容,以及例外正则式
        String PartyWithoutKuoHao = clearContent(judgeData.getParty());// 被告:清除各种括号及括号内的内容
        String EntName = judgeData.getEntname() == null ? "" : judgeData.getEntname();// 企业名称
        String EntNameWithoutKuoHao = clearContent(EntName);// 企业名称:清除各种括号的EntName//正则运算中，与清洗了结构一致，不带括号等等
        String LawStatus = clearContent(judgeData.getLawstatus());// 诉讼地位:清除各种括号及括号内的内容  新增sf字段

        LegalDataAdded J8_AJJGDKHYX = new LegalDataAdded();
        SetParamCommon(J8_AJJGDKHYX, Serialno, "sentenceEffect", "案件结果对客户的影响", EntName, reqID,businessID);// 设置7项数据
        String[] ajdkeyxArr = {"16"};
        try {
            if (CommonUtil.equals(ajdkeyxArr, Ptype)) {
                String SentenceBrief = J4_SLJG.getLegalvalue();
                if (SentenceBrief == ANYNASIS_BLANK) {
                    J8_AJJGDKHYX.setLegalvalue(ANYNASIS_BLANK);
                    J8_AJJGDKHYX.setValuelabel(ANYNASIS_BLANK);
                    J8_AJJGDKHYX.setFlag("0");

                } else if (SentenceBrief != null && !SentenceBrief.contains("、")) {
                    Map<String, Object> paramMap = new HashMap<>();
                    if (impactOfResultUtil(PlaintiffWithoutKuoHao, EntNameWithoutKuoHao)) {
                        paramMap.put("role", "原告");
                    } else if (impactOfResultUtil(PartyWithoutKuoHao, EntNameWithoutKuoHao)) {
                        paramMap.put("role", "被告");
                    } else if (LawStatus.contains("第三") || LawStatus.contains("案外人")) {
                        paramMap.put("role", "无关第三方");
                    } else {
                        paramMap.put("role", "其他");
                    }
                    paramMap.put("resultCode", SentenceBrief);

                    SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                    //通过Reources来获取我们的配置文件
                    InputStream resourceAsStream = Resources.getResourceAsStream("sqlmapconfig.xml");
                    SqlSessionFactory sqlSessionFactory = builder.build(resourceAsStream);
                    SqlSession sqlSession = sqlSessionFactory.openSession();

                    //案件结果对客户的影响
                    JudgeAnynasisReadDao mapper = sqlSession.getMapper(JudgeAnynasisReadDao.class);
                    Map<String, Object> resultMap = mapper.resultImpact(paramMap);

                    if (resultMap != null) {
                        J8_AJJGDKHYX.setValuelabel((String) resultMap.get("IMPACT"));
                        String code = (String) resultMap.get("CODE");
                        J8_AJJGDKHYX.setLegalvalue(code);
                        if ("99".equals(code)) {
                            J8_AJJGDKHYX.setFlag("1");
                            J8_AJJGDKHYX.setValuelabel("无法确定");
                        } else {
                            J8_AJJGDKHYX.setFlag("0");
                        }
                    } else {
                        logger.info("***************“EXCEPTION：数据库没有对应的”案件结果对客户的影响“**************");
                        J8_AJJGDKHYX.setLegalvalue(ANYNASIS_BLANK);
                        J8_AJJGDKHYX.setValuelabel(ANYNASIS_BLANK);
                        J8_AJJGDKHYX.setFlag(ANYNASIS_BLANK);
                    }
                    //sqlSession.commit();
                    sqlSession.commit();
                } else {
                    J8_AJJGDKHYX.setLegalvalue("99");
                    J8_AJJGDKHYX.setValuelabel("无法确定");
                    J8_AJJGDKHYX.setFlag("1");
                }

            } else {
                J8_AJJGDKHYX.setLegalvalue(ANYNASIS_BLANK);
                J8_AJJGDKHYX.setValuelabel(ANYNASIS_BLANK);
                J8_AJJGDKHYX.setFlag(ANYNASIS_BLANK);
            }

        } catch (Exception e) {
            throw new ServiceException("处理案件结果对客户的影响异常:" + e);
            // e.printStackTrace();
        }
        return J8_AJJGDKHYX;
    }

    /**
     * @Description:Plaintiff原告 Party被告
     * @param: [EntName]
     * @return: boolean
     * @author:Yxx
     * @Date: 2018/11/26 14:15
     */
    private static boolean impactOfResultUtil(String column, String EntName) {
        boolean flag = Boolean.FALSE;
        if (StringUtils.isEmpty(EntName) || StringUtils.isEmpty(column)) {
            return flag;
        }
        String arr[] = column.split("[、，,;；]");// \ 和 、全角半角
        if (arr.length > 0) {
            if (CommonUtil.equals(arr, EntName)) {
                flag = Boolean.TRUE;
            }
        }
        return flag;
    }

    /**
     * @Description: 7，案件影响力
     * @param: [judgeData, J6_FYDJ, J5_SLCX，reqID，businessID]
     * @return: LegalDataAdded
     * @author:Yxx
     * @Date: 2018/11/29 13:11
     */
    private static LegalDataAdded getCaseInfluence(LegalStructuredData judgeData, LegalDataAdded J6_FYDJ, LegalDataAdded J5_SLCX, String reqID, String businessID) {
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno() == null ? "" : judgeData.getSerialno().trim();// 序列号
        // 注意：正则匹配使用清洗了的数据
        String EntName = judgeData.getEntname() == null ? "" : judgeData.getEntname();// 企业名称 +

        LegalDataAdded J7_AJYXL = new LegalDataAdded();
        SetParamCommon(J7_AJYXL, Serialno, "caseImpact", "案件影响力", EntName, reqID,businessID);// 设置7项数据
        String[] ajyxlArr = {"14", "15", "16"};
        try {
            if (CommonUtil.equals(ajyxlArr, Ptype)) {
                String CourtLevel = J6_FYDJ.getLegalvalue();
                String Phase = J5_SLCX.getLegalvalue();
                if (CourtLevel == ANYNASIS_BLANK || Phase == ANYNASIS_BLANK) {
                    J7_AJYXL.setLegalvalue(ANYNASIS_BLANK);
                    J7_AJYXL.setValuelabel(ANYNASIS_BLANK);
                    J7_AJYXL.setFlag("1");
                } else {
                    //增加sf新需求
                    if (CourtLevel.equals("其他")) {
                        //案件影响力为空
                        J7_AJYXL.setLegalvalue(ANYNASIS_BLANK);
                        J7_AJYXL.setValuelabel(ANYNASIS_BLANK);
                        J7_AJYXL.setFlag("1");
                    } else if (CourtLevel.equals("4") && Phase.equals("一审")) {
                        J7_AJYXL.setLegalvalue("4");
                        J7_AJYXL.setValuelabel("4");
                        J7_AJYXL.setFlag("0");
                    } else if (CourtLevel.equals("3") && Phase.equals("一审")) {
                        J7_AJYXL.setLegalvalue("3");
                        J7_AJYXL.setValuelabel("3");
                        J7_AJYXL.setFlag("0");
                    } else if (CourtLevel.equals("2") && Phase.equals("一审")) {
                        J7_AJYXL.setLegalvalue("2");
                        J7_AJYXL.setValuelabel("2");
                        J7_AJYXL.setFlag("0");
                    } else if (CourtLevel.equals("1") && Phase.equals("一审")) {
                        J7_AJYXL.setLegalvalue("1");
                        J7_AJYXL.setValuelabel("1");
                        J7_AJYXL.setFlag("0");
                    } else {
                        J7_AJYXL.setLegalvalue("0");
                        J7_AJYXL.setValuelabel("0");
                        //增加sf新需求 将1变更为0
                        J7_AJYXL.setFlag("0");
                    }
                }
            } else {
                J7_AJYXL.setLegalvalue(ANYNASIS_BLANK);
                J7_AJYXL.setValuelabel(ANYNASIS_BLANK);
                J7_AJYXL.setFlag(ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            throw new ServiceException("处理案件影响力异常");
        }
        return J7_AJYXL;
    }

    /**
     * @Description: 6、法院等级
     * @param: [judgeData, reqID, businessID]
     * @return: LegalDataAdded
     * @author:Yxx
     * @Date: 2018/11/29 9:47
     */
    private static LegalDataAdded getCourtLevel(LegalStructuredData judgeData, String reqID, String businessID) {
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno() == null ? "" : judgeData.getSerialno().trim();// 序列号
        String Court = judgeData.getCourt() == null ? "" : judgeData.getCourt().trim();// 法院

        // 注意：正则匹配使用清洗了的数据
        String Caseno = judgeData.getCaseno() == null ? "" : judgeData.getCaseno();//新增sf新需求  案号
        String CasenoWithoutKuoHao = clearContent(judgeData.getCaseno());//新增sf新需求  案号

        String EntName = judgeData.getEntname() == null ? "" : judgeData.getEntname();// 企业名称

        LegalDataAdded J6_FYDJ = new LegalDataAdded();
        SetParamCommon(J6_FYDJ, Serialno, "courtLevel", "法院等级", EntName, reqID,businessID);// 设置7项数据
        String[] fydjArr = {"14", "15", "16"};
        try {
            if (CommonUtil.equals(fydjArr, Ptype)) {
                if (!CommonUtil.hasChar(Court)) {
                    if (!CommonUtil.hasChar(Caseno)) {//空值 ！
                        SetParamSpecial(J6_FYDJ, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                    } else if (CommonUtil.hasChar(Caseno)) {
                        String regexArray[] = {"r5001", "r5002", "r5003", "r5004"};
                        loop4:
                        for (String correspond : regexArray) {
                            String regex = REGEXMAP_FYDJ.get(correspond).replaceAll("【法院等级】", CasenoWithoutKuoHao);
                            if (Pattern.matches(regex, "r5001")) {
                                J6_FYDJ.setLegalvalue("4");
                                J6_FYDJ.setValuelabel("最高");
                                J6_FYDJ.setFlag("0");
                            } else if (Pattern.matches(regex, "r5002")) {
                                J6_FYDJ.setLegalvalue("3");
                                J6_FYDJ.setValuelabel("高级");
                                J6_FYDJ.setFlag("0");
                            } else if (Pattern.matches(regex, "r5003")) {
                                J6_FYDJ.setLegalvalue("2");
                                J6_FYDJ.setValuelabel("中级");
                                J6_FYDJ.setFlag("0");
                            } else if (Pattern.matches(regex, "r5004")) {
                                J6_FYDJ.setLegalvalue("1");
                                J6_FYDJ.setValuelabel("基层");
                                J6_FYDJ.setFlag("0");
                            } else if (Caseno.contains("初")) {
                                J6_FYDJ.setLegalvalue("1");
                                J6_FYDJ.setValuelabel("基层");
                                J6_FYDJ.setFlag("0");
                            } else if (Caseno.contains("终")) {
                                J6_FYDJ.setLegalvalue("2");
                                J6_FYDJ.setValuelabel("中级");
                                J6_FYDJ.setFlag("0");
                            } else {
                                J6_FYDJ.setLegalvalue("99");
                                J6_FYDJ.setValuelabel("其他");
                                J6_FYDJ.setFlag("1");
                            }
                            continue loop4;
                        }
                    }
                } else if (CommonUtil.hasChar(Court)) {
                    if (Court.contains("最高")) {
                        J6_FYDJ.setLegalvalue("4");
                        J6_FYDJ.setValuelabel("最高");
                        J6_FYDJ.setFlag("0");
                    } else if (Court.contains("高级")) {
                        J6_FYDJ.setLegalvalue("3");
                        J6_FYDJ.setValuelabel("高级");
                        J6_FYDJ.setFlag("0");
                    } else if (Court.contains("中级")) {
                        J6_FYDJ.setLegalvalue("2");
                        J6_FYDJ.setValuelabel("中级");
                        J6_FYDJ.setFlag("0");
                    } else if (CommonUtil.contains(new String[]{"区", "县", "市"}, Court)) {
                        J6_FYDJ.setLegalvalue("1");
                        J6_FYDJ.setValuelabel("基层");
                        J6_FYDJ.setFlag("0");
                    } else if (CommonUtil.hasChar(Caseno)) {
                        String regexArray[] = {"r5001", "r5002", "r5003", "r5004"};
                        loop4:
                        for (String correspond : regexArray) {
                            String regex = REGEXMAP_FYDJ.get(correspond).replaceAll("【法院等级】", CasenoWithoutKuoHao);
                            if (Pattern.matches(regex, "r5001")) {
                                J6_FYDJ.setLegalvalue("4");
                                J6_FYDJ.setValuelabel("最高");
                                J6_FYDJ.setFlag("0");
                            } else if (Pattern.matches(regex, "r5002")) {
                                J6_FYDJ.setLegalvalue("3");
                                J6_FYDJ.setValuelabel("高级");
                                J6_FYDJ.setFlag("0");
                            } else if (Pattern.matches(regex, "r5003")) {
                                J6_FYDJ.setLegalvalue("2");
                                J6_FYDJ.setValuelabel("中级");
                                J6_FYDJ.setFlag("0");
                            } else if (Pattern.matches(regex, "r5004")) {
                                J6_FYDJ.setLegalvalue("1");
                                J6_FYDJ.setValuelabel("基层");
                                J6_FYDJ.setFlag("0");
                            } else if (Caseno.contains("初")) {
                                J6_FYDJ.setLegalvalue("1");
                                J6_FYDJ.setValuelabel("基层");
                                J6_FYDJ.setFlag("0");
                            } else if (Caseno.contains("终")) {
                                J6_FYDJ.setLegalvalue("2");
                                J6_FYDJ.setValuelabel("中级");
                                J6_FYDJ.setFlag("0");
                            } else {
                                J6_FYDJ.setLegalvalue("99");
                                J6_FYDJ.setValuelabel("其他");
                                J6_FYDJ.setFlag("1");
                            }
                            continue loop4;
                        }
                    } else {
                        J6_FYDJ.setLegalvalue("99");
                        J6_FYDJ.setValuelabel("其他");
                        J6_FYDJ.setFlag("1");
                    }
                }
            } else {
                J6_FYDJ.setLegalvalue(ANYNASIS_BLANK);
                J6_FYDJ.setValuelabel(ANYNASIS_BLANK);
                J6_FYDJ.setFlag(ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            throw new ServiceException("处理法院等级异常");
        }
        return J6_FYDJ;
    }

    /**
     * @Description: 5、审理程序
     * @param: [judgeData, reqID, businessID]
     * @return: LegalDataAdded
     * @author:Yxx
     * @Date: 2018/12/4 9:20
     */
    private static LegalDataAdded getJudgementProcedure(LegalStructuredData judgeData, String reqID, String businessID) {
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno() == null ? "" : judgeData.getSerialno().trim();// 序列号
        String Caseno = judgeData.getCaseno() == null ? "" : judgeData.getCaseno().trim();// 案号

        // 注意：正则匹配使用清洗了的数据
        String EntName = judgeData.getEntname() == null ? "" : judgeData.getEntname();// 企业名称

        LegalDataAdded J5_SLCX = new LegalDataAdded();
        SetParamCommon(J5_SLCX, Serialno, "phase", "审理程序", EntName, reqID,businessID);// 设置7项数据
        String[] slcxArr = {"14", "15", "16"};
        try {
            //审理程序 司法解析 新需求
            if (CommonUtil.equals(slcxArr, Ptype)) {
                if (CommonUtil.hasChar(Caseno)) {
                    if (Caseno.contains("初")) {
                        J5_SLCX.setLegalvalue("一审");
                        J5_SLCX.setFlag("0");
                        J5_SLCX.setValuelabel("一审");

                    } else if (Caseno.contains("终")) {
                        J5_SLCX.setLegalvalue("二审");
                        J5_SLCX.setFlag("0");
                        J5_SLCX.setValuelabel("二审");
                    } else if (CommonUtil.contains(new String[]{"监", "再", "申", "提", "抗"}, Caseno)) {
                        J5_SLCX.setLegalvalue("再审");
                        J5_SLCX.setFlag("0");
                        J5_SLCX.setValuelabel("再审");
                    } else {
                        J5_SLCX.setLegalvalue("一审");
                        J5_SLCX.setFlag("0");
                        J5_SLCX.setValuelabel("一审");
                    }
                } else {
                    J5_SLCX.setLegalvalue(ANYNASIS_BLANK);
                    J5_SLCX.setValuelabel(ANYNASIS_BLANK);
                    J5_SLCX.setFlag("0");
                }

            } else {
                J5_SLCX.setLegalvalue(ANYNASIS_BLANK);
                J5_SLCX.setValuelabel(ANYNASIS_BLANK);
                J5_SLCX.setFlag(ANYNASIS_BLANK);
            }

        } catch (Exception e) {
            throw new ServiceException("处理审理程序异常");
        }
        return J5_SLCX;
    }

    /**
     * @Description: 2,涉案金额  3，涉案金额单位
     * @param: [judgeData, type, reqID，businessID]
     * @return: LegalDataAdded
     * @author:Yxx
     * @Date: 2018/12/8 10:07
     */
    private LegalDataAdded getInvolvedMoneyorUnity(LegalStructuredData judgeData, String type, String reqID, String businessID) {

        /** ==========================使用到的安硕原始数据项=========================== */
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno() == null ? "" : judgeData.getSerialno().trim();// 序列号

        // 注意：正则匹配使用清洗了的数据
        String plaintiff = judgeData.getPlaintiff() == null ? "" : judgeData.getPlaintiff().trim();
        String plaintiffWithoutKuoHao = clearContent(plaintiff);// 原告:清除各种括号及括号内的内容,以及例外正则式
        String party = judgeData.getParty() == null ? "" : judgeData.getParty().trim();
        String partyWithoutKuoHao = clearContent(party);// 被告:清除各种括号及括号内的内容
        String judgementresult = judgeData.getJudgementresult() == null ? "" : judgeData.getJudgementresult().trim();// 判决结果
        String judgeWithoutKuoHao = clearContent(judgementresult);// 判决结果:清除各种括号及括号内的内容
        String entName = judgeData.getEntname() == null ? "" : judgeData.getEntname().trim();// 企业名称
        String entNameWithoutKuoHao = clearContent(entName);// 企业名称:清除各种括号的EntName//正则运算中，与清洗了结构一致，不带括号等等
        String pdesc = judgeData.getPdesc() == null ? "" : judgeData.getPdesc().trim();// 公告内容
        String pdescKuoHao = clearContent(pdesc);// 公告内容:清除各种括号及括号内的内容

        String lawstatus = judgeData.getLawstatus() == null ? "" : judgeData.getLawstatus().trim();// 诉讼地位

        LegalDataAdded resultLegalDataAdded = new LegalDataAdded();

        LegalDataAdded involvedMoney = new LegalDataAdded();
        LegalDataAdded involvedMoneyorUnity = new LegalDataAdded();
        String[] sajeArr = { "16" };
        // 新增
        String ryjeRegex = ".*?(?:(?:\\d*[,，]*)*\\d*\\.?\\d*|[零一二三四五六七八九十百千万亿]*)(?:元|美元|港元|澳门元|日元|韩元|缅元|马元|新加坡元|欧元|英镑|马克|法郎|卢布|加元|新西兰元|澳元|澳大利亚元).*?";
        String pdescRegex = ".*?(?:判决).{0,5}?[:：].*";
        SetParamCommon(involvedMoney, Serialno, "payment", "涉案金额", entName, reqID,businessID);// 设置7项数据
        SetParamCommon(involvedMoneyorUnity, Serialno, "payUnit", "涉案金额单位", entName, reqID,businessID);// 设置7项数据
        try {
            // 验证Ptype=16
            if (CommonUtil.equals(sajeArr, Ptype)) {
                // 验证诉讼地位
                if (lawstatus.contains("第三") || lawstatus.contains("案外人")) {
                    SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                    SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                } else {
                    // 验证公告内容
                    if (!CommonUtil.hasChar(pdescKuoHao)) {
                        SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                        SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                    } else {
                        // 验证判决结果
                        if (!CommonUtil.hasChar(judgeWithoutKuoHao)) {
                            // 判决结果为空，公告内容不为空时：先验证公告内容是否包含一系列字符
                            if (!(pdescKuoHao.contains("判决书") || pdescKuoHao.contains("裁定书")
                                    || pdescKuoHao.contains("调解书") || pdescKuoHao.contains("通知书")
                                    || pdescKuoHao.contains("决定书") || pdescKuoHao.contains("建议书")
                                    || pdescKuoHao.contains("强制令") || pdescKuoHao.contains("意见书")
                                    || pdescKuoHao.contains("决定书") || pdescKuoHao.contains("告知书"))) {
                                SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                                SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                            } else {
                                // 公告内容中"任意金额的判断"
                                if (!(Pattern.matches(ryjeRegex, pdescKuoHao))) {
                                    SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                                    SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                                } else if ((Pattern.matches(ryjeRegex, pdescKuoHao))) {
                                    // 提取公告内容中判决结果的文本
                                    pdescKuoHao = pdescKuoHao.replaceAll(":", "：");
                                    String[] pdescKuoHaoArr = pdescKuoHao.split("：");
                                    for (int i = 0; i < pdescKuoHaoArr.length; i++) {
                                        if (!(pdescKuoHaoArr[i].matches(pdescRegex))) {
                                            pdescKuoHao = pdescKuoHao.replaceAll(pdescKuoHaoArr[i].toString() + "：","");
                                        } else {
                                            break;
                                        }
                                    }
                                    //System.out.println(pdescKuoHao);
                                    if (containsNumber(pdescKuoHao)) {// 含有数字
                                        List<String> sonModeList = analysisMoney(pdescKuoHao, Serialno,
                                                entNameWithoutKuoHao, plaintiffWithoutKuoHao, partyWithoutKuoHao);

                                        // 处理汇总的子模式结果:sonModeList
                                        if (sonModeList == null || sonModeList.size() == 0) {
                                            SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "1");
                                            SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "1");
                                        } else {
                                            double finalMoney = 0;
                                            String finalUnit = "元";// 最终单位以“元”开始
                                            for (String moneyUnit : sonModeList) {
                                                String moneyAndUnit[] = MoneyAndUnit(moneyUnit);// 相加，处理千分位和中文，单位问题
                                                String money = moneyAndUnit[0];
                                                String unit = moneyAndUnit[1];
                                                // 获取统一单位【1，有外币以外币为准，除去人民币；2，多种外币，以第一种为准】
                                                if (!"0".equals(money)) {
                                                    if (finalUnit.equals(unit)) {
                                                        finalMoney += Double.parseDouble(money);
                                                    } else {// 当前与最终单位不同
                                                        if (finalUnit.equals("元")) {// 当前最终单位是元，新的单位不是元》》》取代
                                                            finalMoney = Double.parseDouble(money);
                                                            finalUnit = unit;
                                                        }
                                                        // 当前最终单位不是元》》》不可取代,不处理（取第一个外币）
                                                    }
                                                }
                                            }
                                            String moneyString = CommonUtil.getDoubleString(finalMoney);
                                            SetParamSpecial(involvedMoney, moneyString, moneyString, "0");
                                            SetParamSpecial(involvedMoneyorUnity, finalUnit, finalUnit, "0");
                                        }

                                    } else {
                                        SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                                        SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                                    }
                                }
                            }
                        } else {
                            // 判决结果中"任意金额"的正则解析判断
                            if ((Pattern.matches(ryjeRegex, judgeWithoutKuoHao))) {
                                // 判决结果的正则解析
                                if (containsNumber(judgeWithoutKuoHao)) {// 含有数字
                                    List<String> sonModeList = analysisMoney(judgeWithoutKuoHao, Serialno,
                                            entNameWithoutKuoHao, plaintiffWithoutKuoHao, partyWithoutKuoHao);
                                    // 处理汇总的子模式结果:sonModeList
                                    if (sonModeList == null || sonModeList.size() == 0) {
                                        SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "1");
                                        SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "1");
                                    } else {
                                        double finalMoney = 0;
                                        String finalUnit = "元";// 最终单位以“元”开始
                                        for (String moneyUnit : sonModeList) {
                                            String moneyAndUnit[] = MoneyAndUnit(moneyUnit);// 相加，处理千分位和中文，单位问题
                                            String money = moneyAndUnit[0];
                                            String unit = moneyAndUnit[1];
                                            // 获取统一单位【1，有外币以外币为准，除去人民币；2，多种外币，以第一种为准】
                                            if (!"0".equals(money)) {
                                                if (finalUnit.equals(unit)) {
                                                    finalMoney += Double.parseDouble(money);
                                                } else {// 当前与最终单位不同
                                                    if (finalUnit.equals("元")) {// 当前最终单位是元，新的单位不是元》》》取代
                                                        finalMoney = Double.parseDouble(money);
                                                        finalUnit = unit;
                                                    }
                                                    // 当前最终单位不是元》》》不可取代,不处理（取第一个外币）
                                                }
                                            }
                                        }

                                        String moneyString = CommonUtil.getDoubleString(finalMoney);
                                        SetParamSpecial(involvedMoney, moneyString, moneyString, "0");
                                        SetParamSpecial(involvedMoneyorUnity, finalUnit, finalUnit, "0");
                                    }

                                } else {
                                    SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                                    SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                                }
                            }
                        }
                    }
                }

            } else {// pType != 16
                SetParamSpecial(involvedMoney, ANYNASIS_BLANK, ANYNASIS_BLANK, ANYNASIS_BLANK);
                SetParamSpecial(involvedMoneyorUnity, ANYNASIS_BLANK, ANYNASIS_BLANK, ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            throw new ServiceException("处理涉案金额+涉案金额单位异常:" + e);
        }

        if (type.equals("SAJE")) {
            resultLegalDataAdded = involvedMoney;
        } else if (type.equals("SAJEDW")) {
            resultLegalDataAdded = involvedMoneyorUnity;
        }
        return resultLegalDataAdded;
    }

    /**
     * @Description: 涉案金额的解析规则
     * @param: [paramKuoHao, Serialno, entNameWithoutKuoHao，plaintiffWithoutKuoHao, partyWithoutKuoHao]
     * @return: List<String>
     * @author:Yxx
     * @Date: 2018/12/19 15:30
     */
    public List<String> analysisMoney(String paramKuoHao, String Serialno, String entNameWithoutKuoHao,
                                      String plaintiffWithoutKuoHao, String partyWithoutKuoHao) {

        paramKuoHao = removeExpectNumAndReg(paramKuoHao);// 清除例外不取的正则表达式
        String regexSplit = "[;；。]";// 按；;。分割公告内容
        String results[] = paramKuoHao.split(regexSplit);

        List<String> sonModeList = new ArrayList<>();// 子模式结果List
        /************************************** 开始处理每个子句 *****************************/
        for (int i = 0; i < results.length; i++) {
            String singleResult = results[i];
            // if(!containsNumber(singleResult)){continue;}//不用处理单句，否则会将连带责任过滤掉
            // 判断三个步骤是否往下进行的标志，若是step(i)获取到金额，修改该标志的值，该子句处理完毕
            boolean executeFlag = Boolean.TRUE;
            // ***step1***/
            String step1[] = { "r11", /* 动态：企业名称 */
                    "r12", /* 动态：企业名称 */
                    "r13", /* 动态：企业名称 */
                    "r21", /* 动态：企业名称 */
                    "r22", /* 动态：企业名称 */
                    "r31", /* 动态：企业名称 */
                    "r32", /* 动态：企业名称 */
                    "r33"/* 动态：企业名称 */
            };
            for (String regexName : step1) {
                String sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                sourceRegex = sourceRegex.replaceAll("【企业名称】", entNameWithoutKuoHao);// 替换企业名称

                Pattern pattern = Pattern.compile(sourceRegex);
                Matcher matcher = pattern.matcher(singleResult);
                if (matcher.find()) {
                    sonModeList.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                    executeFlag = Boolean.FALSE;// 修改状态值，step2，step3不执行
                    logger.info("Serialno:" + Serialno + "【涉案金额step1】***【" + singleResult + "】:正则【" + regexName
                            + "】匹配到子串！！！！！");
                    break;
                }
            }

            // ***step2***/
            if (executeFlag) {
                boolean MultPlaintiff = CommonUtil.contains(new String[] { "、", "；", "，", ",", ";" },
                        plaintiffWithoutKuoHao);// 原告是否只有一个
                boolean MultParty = CommonUtil.contains(new String[] { "、", "；", "，", ",", ";" }, partyWithoutKuoHao);// 被告是否只有一个
                if (!MultPlaintiff && !MultParty) {// 如果均等于1处理step2
                    String step2[] = { "r011", "r012", "r013", "r021", "r022", "r031", "r032", "r033" };
                    // PS：在step2的正则表达式中，没有动态替换的内容
                    for (String regexName : step2) {
                        String sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                        Pattern pattern = Pattern.compile(sourceRegex);
                        Matcher matcher = pattern.matcher(singleResult);

                        if (matcher.find()) {
                            sonModeList.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                            executeFlag = Boolean.FALSE;// 修修改状态值，step3不执行
                            logger.info("Serialno:" + Serialno + "【涉案金额step2】***【" + singleResult + "】:正则【" + regexName
                                    + "】匹配到子串!!!");
                            break;
                        }

                    }

                }

            }

            // ***step3***/
            if (executeFlag) {
                String sourceRegex = REGEXMAP_SAJE.get("r43");// 原始正则式
                sourceRegex = sourceRegex.replaceAll("【企业名称】", entNameWithoutKuoHao);// 替换企业名称

                String sourceRegexR44 = REGEXMAP_SAJE.get("r44");// 原始正则式
                sourceRegexR44 = sourceRegexR44.replaceAll("【企业名称】", entNameWithoutKuoHao);// 替换企业名称

                if (Pattern.compile(sourceRegex).matcher(singleResult).matches()) {// r43
                    // r401，r402,r403,3个正则式式子无需替换，均为静态字符串
                    if (Pattern.compile(REGEXMAP_SAJE.get("r401")).matcher(singleResult).matches()) {// r401
                        String step3_401[] = { "r011", "r012", "r013" };
                        // PS：在step3_401的正则表达式中，没有动态替换的内容
                        for (String regexName : step3_401) {
                            sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                            Pattern pattern = Pattern.compile(sourceRegex);
                            Matcher matcher = pattern.matcher(singleResult);

                            if (matcher.find()) {
                                sonModeList.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                logger.info("Serialno:" + Serialno + "【涉案金额step3】***【" + singleResult + "】:正则【"
                                        + regexName + "】匹配到子串!!!");
                                break;
                            }

                        }
                    } else if (Pattern.compile(REGEXMAP_SAJE.get("r402")).matcher(singleResult).matches()) {// r402
                        String step3_402[] = { "r011", "r012", "r013" };
                        // PS：在step3_401的正则表达式中，没有动态替换的内容
                        for (String regexName : step3_402) {
                            sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                            Pattern pattern = Pattern.compile(sourceRegex);
                            Matcher matcher = pattern.matcher(singleResult);

                            if (matcher.find()) {
                                sonModeList.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                logger.info("Serialno:" + Serialno + "【涉案金额step3】***【" + singleResult + "】:正则【"
                                        + regexName + "】匹配到子串!!!");
                                break;
                            }

                        }

                    } else if (Pattern.compile(REGEXMAP_SAJE.get("r403")).matcher(singleResult).matches()) {// r403
                        String step3_403[] = { "r011", "r012", "r013" };
                        // PS：在step3_401的正则表达式中，没有动态替换的内容
                        for (String regexName : step3_403) {
                            sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                            Pattern pattern = Pattern.compile(sourceRegex);
                            Matcher matcher = pattern.matcher(singleResult);

                            if (matcher.find()) {
                                sonModeList.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                logger.info("Serialno:" + Serialno + "【涉案金额step3】***【" + singleResult + "】:正则【"
                                        + regexName + "】匹配到子串!!!");
                                break;
                            }

                        }

                    } else if (Pattern.compile(sourceRegexR44).matcher(singleResult).matches()) {// r44
                        // 循环子句0到i:i是当前匹配成功r44的子句下标
                        // 匹配到金额，停止循环标志
                        for (int j = 0; j <= i; j++) {
                            String singleResultR44 = results[j];// 从0循环到i
                            String step3_44[] = { "r011", "r012", "r013" };
                            // PS：在step3_401的正则表达式中，没有动态替换的内容
                            for (String regexName : step3_44) {
                                sourceRegex = REGEXMAP_SAJE.get(regexName);// 原始正则式
                                Pattern pattern = Pattern.compile(sourceRegex);
                                Matcher matcher = pattern.matcher(singleResultR44);

                                if (matcher.find()) {
                                    sonModeList.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                    logger.info("Serialno:" + Serialno + "【涉案金额step3】***【" + singleResultR44 + "】:正则【"
                                            + regexName + "】匹配到子串!!!");
                                    break;
                                }

                            }
                        }
                    } else if (CommonUtil.contains(new String[] { ",", ";", "，", "；", "、" }, partyWithoutKuoHao)) {// 被告人不止一个
                        String PartyList[] = partyWithoutKuoHao.split("[、，；,;]");
                        for (String PartyName : PartyList) {
                            String regexR405 = REGEXMAP_SAJE.get("r405").replaceAll("【被告名称】", PartyName);
                            if (Pattern.compile(regexR405).matcher(singleResult).matches()
                                    && !PartyName.equals(entNameWithoutKuoHao)) {// 匹配r405并且被告人！=
                                // 企业名称
                                String regex3_405[] = { // 被告名称
                                        "r406", "r407", "r408" };
                                // 循环匹配子句：0到i
                                for (int j = 0; j <= i; j++) {
                                    String singleResultThis = results[j];// 从0循环到i
                                    for (String regexName : regex3_405) {
                                        String regex = REGEXMAP_SAJE.get(regexName).replaceAll("【被告名称】", PartyName);
                                        Pattern pattern = Pattern.compile(regex);
                                        Matcher matcher = pattern.matcher(singleResultThis);

                                        if (matcher.matches()) {
                                            sonModeList.add(matcher.group(1));// 分析正则式，得知第一个捕获组是我们要的（金额+金额单位）
                                            logger.info("Serialno:" + Serialno + "【涉案金额step3】***【" + singleResultThis
                                                    + "】:正则【" + regexName + "】匹配到子串!!!");
                                            break;
                                        }
                                    }
                                }

                            }

                        }

                    }
                }

            }
        }
        /************************************** 结束处理每个子句 *****************************/
        return sonModeList;
    }

    /**
     * @Description: 分解金额和单位 (子模式，即：子模式= "金额数值+金额单位"的形式（如"2000美元"）基础正则配置表)
     * @param: [moneyUnit]
     * @return: String[]
     * @author:Yxx
     * @Date: 2018/12/19 15:30
     */
    private static String[] MoneyAndUnit(String moneyUnit) {
        String moneyAndUnit[] = new String[2];
        String regex = "(" + SAJE_TARGET + ")(" + SAJE_UNIT + ")";// (金额)(单位)格式
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(moneyUnit);
        if (matcher.matches()) {// ((?:\d*[,，]*)*\d*\.?\d*[零一二三四五六七八九十百千万亿]*)(元|美元|港元|澳门元|日元|韩元|缅元|马元|新加坡元|欧元|英镑|马克|法郎|卢布|加元|新西兰元|澳元|澳大利亚元)
            moneyAndUnit[0] = matcher.group(1);// 金额
            moneyAndUnit[1] = matcher.group(2);// 单位
        }
        // 处理金额格式：全部转化为数字（无千分位）
        moneyAndUnit[0] = CNNMFilter.getAllNumber(moneyAndUnit[0]);
        return moneyAndUnit;
    }

    /**
     * @Description: 涉案金额--判决结果 = 清除例外不取的正则表达式
     * @param: [result]
     * @return: String
     * @author:Yxx
     * @Date: 2018/12/4 10:30
     */
    private static String removeExpectNumAndReg(String result) {
        // 2个正则清除顺序不可乱
        String badNum = "([0-9\\.,，]+)(?!([0-9\\.,，]+|([零一二三四五六七八九十百千万亿]*(元|美元|港元|澳门元|日元|韩元|缅元|马元|新加坡元|欧元|英镑|马克|法郎|卢布|加元|新西兰元|澳元|澳大利亚元))))";
        String badReg = "(?:\\d*[,，]*)*\\d+\\.?\\d*万*元为(?:基数|本金)";// 清除无关金额长数字
        result = result.replaceAll(badNum, "*").replaceAll(badReg, "#");// 清除无关金额
        return result;
    }

    /**
     * @Description: 字符串中是否包含数字
     * @param: [s]
     * @return: boolean
     * @author:Yxx
     * @Date: 2018/12/5  14:33
     */
    private static boolean containsNumber(String s) {
        if (s == null || s.equals("")) {
            return Boolean.FALSE;
        }

        if (CommonUtil.hasChar(s)) {
            return Pattern.compile("[0-9]").matcher(s).find();
        }

        return Boolean.FALSE;
    }

    /**
     * @Description: 4，审理结果
     * @param: [judgeData, reqID, businessID]
     * @return: LegalDataAdded
     * @author:Yxx
     * @Date: 2018/12/7 11:00
     */
    private LegalDataAdded getJudgementResult(LegalStructuredData judgeData, String reqID, String businessID) {
        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno() == null ? "" : judgeData.getSerialno().trim();// 序列号

        // 注意：正则匹配使用清洗了的数据
        String PlaintiffWithoutKuoHao = clearContent(judgeData.getPlaintiff());// 原告:清除各种括号及括号内的内容,以及例外正则式
        String PartyWithoutKuoHao = clearContent(judgeData.getParty());// 被告:清除各种括号及括号内的内容
        String Judgementresult = judgeData.getJudgementresult() == null ? "" : judgeData.getJudgementresult();// 判决结果
        String JudgeWithoutKuoHao = clearContent(Judgementresult);// 判决结果:清除各种括号及括号内的内容
        String EntName = judgeData.getEntname() == null ? "" : judgeData.getEntname();// 企业名称
        String EntNameWithoutKuoHao = clearContent(EntName);// 企业名称:清除各种括号的EntName//正则运算中，与清洗了结构一致，不带括号等等
        String Pdesc = judgeData.getPdesc() == null ? "" : judgeData.getPdesc();// 公告内容
        String PdescWithoutKuoHao = clearContent(Pdesc);// 公告内容:清除各种括号及括号内的内容

        String LawStatus = clearContent(judgeData.getLawstatus());// 诉讼地位:清除各种括号及括号内的内容 sf 新增字段

        LegalDataAdded J4_SLJG = new LegalDataAdded();
        SetParamCommon(J4_SLJG, Serialno, "sentenceBrief", "审理结果", EntName, reqID,businessID);// 设置7项数据
        String[] sljgArr = {"16"};
        try {
            if (CommonUtil.equals(sljgArr, Ptype)) {
                if (LawStatus.contains("第三") || LawStatus.contains("案外人")) {
                    SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                } else {
                    if (!CommonUtil.hasChar(PdescWithoutKuoHao)) {

                        SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");

                    } else {
                        if (!CommonUtil.hasChar(JudgeWithoutKuoHao)) {
                            // SetParamSpecial(J4_SLJG, "99", "其他结果", "1");
                            // 如果公告内容不为空，判决结果为空
                            if (!(PdescWithoutKuoHao.contains("判决书") || PdescWithoutKuoHao.contains("裁定书")
                                    || PdescWithoutKuoHao.contains("调解书") || PdescWithoutKuoHao.contains("通知书")
                                    || PdescWithoutKuoHao.contains("决定书") || PdescWithoutKuoHao.contains("建议书")
                                    || PdescWithoutKuoHao.contains("强制令") || PdescWithoutKuoHao.contains("意见书")
                                    || PdescWithoutKuoHao.contains("决定书") || PdescWithoutKuoHao.contains("告知书"))) {
                                SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                            } else {
                                // 符合的正则式名称列表
                                List<String> JudgeResultList = analysisResult(PdescWithoutKuoHao, Serialno, EntNameWithoutKuoHao, PlaintiffWithoutKuoHao, PartyWithoutKuoHao);
                                // 循环结束，获取到所有符合的正则式名称list：JudgeResultList
                                // 根据优先级，查询出优先级最高的审理结果
                                SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                                //通过Reources来获取我们的配置文件
                                InputStream resourceAsStream = Resources.getResourceAsStream("sqlmapconfig.xml");
                                SqlSessionFactory sqlSessionFactory = builder.build(resourceAsStream);
                                SqlSession sqlSession = sqlSessionFactory.openSession();
                                // 根据优先级，查询出优先级最高的审理结果
                                JudgeAnynasisReadDao mapper = sqlSession.getMapper(JudgeAnynasisReadDao.class);
                                List<Map<String, String>> finalList = mapper.judgeResultFinalMap(JudgeResultList);

                                if (finalList == null || finalList.size() == 0) {
                                    logger.info("审理结果未获取到取值映射表数据");
                                    SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                                } else {
                                    if (finalList.size() == 1) {

                                        SetParamSpecial(J4_SLJG, finalList.get(0).get("CODE"),
                                                finalList.get(0).get("DESCRIPTION"), "0");

                                    } else {

                                        SetParamSpecial(J4_SLJG, joinResult(finalList, "CODE"),
                                                joinResult(finalList, "DESCRIPTION"), "0");
                                    }
                                }
                                //sqlSession.commit();
                                sqlSession.close();
                            }
                        } else if (CommonUtil.hasChar(JudgeWithoutKuoHao)) {
                            // 符合的正则式名称列表
                            List<String> JudgeResultList = analysisResult(JudgeWithoutKuoHao, Serialno, EntNameWithoutKuoHao, PlaintiffWithoutKuoHao, PartyWithoutKuoHao);

                            // 循环结束，获取到所有符合的正则式名称list：JudgeResultList
                            // 根据优先级，查询出优先级最高的审理结果
                            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                            //通过Reources来获取我们的配置文件
                            InputStream resourceAsStream = Resources.getResourceAsStream("sqlmapconfig.xml");
                            SqlSessionFactory sqlSessionFactory = builder.build(resourceAsStream);
                            SqlSession sqlSession = sqlSessionFactory.openSession();
                            // 根据优先级，查询出优先级最高的审理结果
                            JudgeAnynasisReadDao mapper = sqlSession.getMapper(JudgeAnynasisReadDao.class);
                            List<Map<String, String>> finalList = mapper.judgeResultFinalMap(JudgeResultList);

                            if (finalList == null || finalList.size() == 0) {
                                logger.info("审理结果未获取到取值映射表数据");
                                SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, "0");
                            } else {
                                if (finalList.size() == 1) {

                                    SetParamSpecial(J4_SLJG, finalList.get(0).get("CODE"),
                                            finalList.get(0).get("DESCRIPTION"), "0");

                                } else {

                                    SetParamSpecial(J4_SLJG, joinResult(finalList, "CODE"),
                                            joinResult(finalList, "DESCRIPTION"), "0");
                                }
                            }
                            //sqlSession.commit();
                            sqlSession.close();
                        }
                    }
                }
            } else {
                SetParamSpecial(J4_SLJG, ANYNASIS_BLANK, ANYNASIS_BLANK, ANYNASIS_BLANK);
            }
        } catch (Exception e) {
            throw new ServiceException("处理审理结果异常");
            //e.printStackTrace();
        }
        return J4_SLJG;
    }

    /**
     * @Description: 审理结果子规则
     * @param: [paramWithoutKuoHao, Serialno, EntNameWithoutKuoHao，PlaintiffWithoutKuoHao，PartyWithoutKuoHao]
     * @return: List<String>
     * @author:Yxx
     * @Date: 2018/12/10 9:50
     */
    private List<String> analysisResult(String paramKuoHao, String Serialno, String EntNameWithoutKuoHao, String PlaintiffWithoutKuoHao, String PartyWithoutKuoHao) {
        String judgement = paramKuoHao.replaceAll("反诉原告", "被告").replaceAll("反诉被告", "原告");
        String regexSplit = "[;；。]";// 按；;。分割判决结果
        String results[] = judgement.split(regexSplit);
        List<String> JudgeResultList = new ArrayList<>();// 符合的正则式名称列表

        loop4:
        for (String singleResult : results) {

            {
                // 合并文档内多个描述，此处按顺序执行，数组元素次序不可更改
                String regexArray[] = {"r0201", "r0301", "r0302", "r0401", "r0402", "r0403", "r0404", // r0404含有【被告】
                        "r0405", "r0501", "r0601", "r0602", "r0603", "r0701"};//新增sf规则
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if ("r0404".equals(singleArray) || "r0405".equals(singleArray)) {
                        regex = regex.replaceAll("【被告】", PartyWithoutKuoHao);
                    }
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }

            {

                String regexArray[] = {"r0901", "r0902", "r0903", "r0904", "r0905", "r0906", "r0907"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            if (EntNameWithoutKuoHao.equals(PartyWithoutKuoHao)) {
                String regexArray[] = {"r0908", "r0909", "r0910", "r0911", "r0912"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            if (EntNameWithoutKuoHao.equals(PlaintiffWithoutKuoHao)) {
                String regexArray[] = {"r0913", "r0914", "r0915", "r0916", "r0917"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            {
                String regexArray[] = {"r1001", "r1002", "r1003", "r1004", "r1005", "r1006", "r1007", "r1008",
                        "r1009"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }

            if (EntNameWithoutKuoHao.equals(PartyWithoutKuoHao)) {
                String regexArray[] = {"r1010", "r1011", "r1012", "r1013", "r1014", "r1015", "r1016", "r1017"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            if (EntNameWithoutKuoHao.equals(PlaintiffWithoutKuoHao)) {
                String regexArray[] = {"r1018", "r1019", "r1020", "r1021", "r1022", "r1023", "r1024", "r1025"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            if (EntNameWithoutKuoHao.equals(PartyWithoutKuoHao)) {
                String regexArray[] = {"r1103", "r1104"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            if (EntNameWithoutKuoHao.equals(PlaintiffWithoutKuoHao)) {
                String regexArray[] = {"r1105", "r1106"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }

            {
                String regexArray[] = {"r1201", "r1202", "r1203"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            if (EntNameWithoutKuoHao.equals(PartyWithoutKuoHao)) {
                String regexArray[] = {"r1204", "r1205", "r1206"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }

            if (EntNameWithoutKuoHao.equals(PlaintiffWithoutKuoHao)) {
                String regexArray[] = {"r1207", "r1208", "r1209"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }

            String partyArray[] = PartyWithoutKuoHao.split("[、;；,，]");
            if (CommonUtil.equals(partyArray, EntNameWithoutKuoHao)) {
                String regexArray[] = {"r1301", "r1401"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            {
                // 合并文档内多个描述，此处按顺序执行，数组元素次序不可更改
                String regexArray[] = {"r1501", "r1601", "r1602", "r1701", "r1702", "r1801", "r1802", "r1901", "r2001",
                        "r2002", "r2003", "r2004", "r2201", "r2202"};
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            {
                String regexArray[] = new String[1];
                if (paramKuoHao.contains(EntNameWithoutKuoHao)) {
                    regexArray[0] = "r2601";
                } else {
                    regexArray[0] = "r2602";// 自添加
                }
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }
            {
                // 合并文档内多个描述，此处按顺序执行，数组元素次序不可更改
                String regexArray[] = {"r2301", "r2302", "r2303", "r2501", "r2502", "r2503", "r2401",
                        "r2402", "r2403", "r2404", "r2405", "r2406", "r2101", "r0801", "r0802", "r0803",
                        "r0101", "r0102", "r0103"};//新增sf规则表
                for (String singleArray : regexArray) {
                    String regex = REGEXMAP_SLJG.get(singleArray).replaceAll("【企业名称】", EntNameWithoutKuoHao);
                    if (Pattern.compile(regex).matcher(singleResult).matches()) {
                        JudgeResultList.add(singleArray);
                        logger.info("Serialno:" + Serialno + "【审理结果】***【" + singleResult + "】:正则【" + singleArray
                                + "】匹配!!!");
                        continue loop4;
                    }
                }
            }

            {
                JudgeResultList.add("r0001");// 其他结果

            }

        }
        return JudgeResultList;
    }


    /**
     * @Description: 拼接审理结果
     * @param: [list, code]
     * @return: String
     * @author:Yxx
     * @Date: 2018/11/29 20:16
     */
    private static String joinResult(List<Map<String, String>> list, String code) {
        StringBuffer s = new StringBuffer();
        for (int i = 0, leng = list.size(); i < leng; i++) {
            s.append(list.get(i).get(code));
            if (i != leng - 1) {
                s.append("、");
            }
        }
        return s.toString();
    }

    /**
     * @Description: 1,案由分类
     * @param: [judgeData, reqID, businessID]
     * @return: LegalDataAdded
     * @author:Yxx
     * @Date: 2018/11/28 10:20
     */
    private LegalDataAdded getCaseReason(LegalStructuredData judgeData, String reqID, String businessID) {

        String Ptype = judgeData.getPtype() == null ? "" : judgeData.getPtype().trim();// 公告类型
        String Serialno = judgeData.getSerialno() == null ? "" : judgeData.getSerialno().trim();// 序列号
        String Caseno = judgeData.getCaseno() == null ? "" : judgeData.getCaseno().trim();// 案号
        String Casereason = judgeData.getCasereason() == null ? "" : judgeData.getCasereason().trim();// 案由

        // 注意：正则匹配使用清洗了的数据
        String EntName = judgeData.getEntname() == null ? "" : judgeData.getEntname();// 企业名称
        LegalDataAdded caseReason = new LegalDataAdded();
        String[] ayflArr = {"14", "15", "16", "17", "18", "22"};
        SetParamCommon(caseReason, Serialno, "caseClass", "案由分类", EntName, reqID, businessID);// 设置7项数据
        Map<String, Object> result = new HashMap<>();
        try {
            if (CommonUtil.equals(ayflArr, Ptype)) {
                if (!CommonUtil.hasChar(Caseno) && !CommonUtil.hasChar(Casereason)) {
                    caseReason.setLegalvalue(ANYNASIS_BLANK);// 案号、案由为空
                    caseReason.setValuelabel(ANYNASIS_BLANK);
                } else {
                    boolean stop = false;
                    if (CommonUtil.hasChar(Caseno)) {
                        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                        //通过Reources来获取我们的配置文件
                        InputStream resourceAsStream = Resources.getResourceAsStream("sqlmapconfig.xml");
                        SqlSessionFactory sqlSessionFactory = builder.build(resourceAsStream);
                        SqlSession sqlSession = sqlSessionFactory.openSession();
                        //案由分类获取取值代码和取值描述
                        JudgeAnynasisReadDao mapper = sqlSession.getMapper(JudgeAnynasisReadDao.class);
                        result = mapper.caseReason(Caseno);
                        if (!CommonUtil.mapIsEmptyOrNull(result)) {
                            stop = true;
                        }
                        //sqlSession.commit();
                        sqlSession.close();
                    }

                    if (!stop && !CommonUtil.contains(new String[]{"刑", "行"}, Caseno) && Casereason != null) {
                        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
                        //通过Reources来获取我们的配置文件
                        InputStream resourceAsStream = Resources.getResourceAsStream("sqlmapconfig.xml");
                        SqlSessionFactory sqlSessionFactory = builder.build(resourceAsStream);
                        SqlSession sqlSession = sqlSessionFactory.openSession();
                        //案由分类获取取值代码和取值描述
                        JudgeAnynasisReadDao mapper = sqlSession.getMapper(JudgeAnynasisReadDao.class);
                        result = mapper.caseReason(Casereason);
                        if (!CommonUtil.mapIsEmptyOrNull(result)) {
                            stop = true;
                        }
                        //sqlSession.commit();
                        sqlSession.close();
                    }

                    if (!stop) {
                        caseReason.setLegalvalue("99");
                        caseReason.setValuelabel("其他案由");
                    }
                }
                if (!CommonUtil.mapIsEmptyOrNull(result)) {
                    caseReason.setLegalvalue((String) result.get("CODE"));
                    caseReason.setValuelabel((String) result.get("DESCRIPTION"));

                }
            } else {
                caseReason.setLegalvalue(ANYNASIS_BLANK);
                caseReason.setValuelabel(ANYNASIS_BLANK);
            }
            caseReason.setFlag(ANYNASIS_BLANK);//是否需要人工审核
            return caseReason;
        } catch (Exception e) {
            throw new ServiceException("处理案由分类异常");
            //e.printStackTrace();
        }

    }

    /**
     * @Description: 设置统一的字段
     * @param: [legalDataAdded, serialno, VarName，VarLabel，EntName，reqId，businessID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/4 11:00
     */
    private static void SetParamCommon(LegalDataAdded legalDataAdded, String serialno, String VarName, String VarLabel, String EntName, String reqID, String businessID) {
        legalDataAdded.setReqID(reqID);
        legalDataAdded.setBusinessID(businessID);
        legalDataAdded.setEntname(EntName);
        legalDataAdded.setSerialno(serialno);
        legalDataAdded.setVarname(VarName);
        legalDataAdded.setVarlabel(VarLabel);

    }

    //测试
    public static void main(String[] args) throws Exception {
        Long t1 = System.currentTimeMillis();
        for (int i = 0; i < 1; i++) {
            String regexExcept = "(?:\\d*[,，]*)*\\d+\\.?\\d*万*元为(?:基数|本金)";
            String ss = "aaaaa以111元为本金支付利息工行四川省自贡分行1234567890123456789012345账户的24万元查封、冻结、扣押措施；";
            String sss = ss.replaceAll(regexExcept, "");
            System.out.println(sss);
        }

        Long t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

        //删除【】（）测试
        String zzz = "aaa.aa(s.)aaa(c.(d.)) 123		4";
        System.out.println(zzz);
        System.out.println(clearContent(zzz));
    }
}
