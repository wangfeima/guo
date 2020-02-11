package com.dfwy.online.sparkstreamingtask.dao;

import common.pojo.legalbean.*;

import java.util.List;
import java.util.Map;

/**
 * @Description:司法解析映射表
 * @author:Yxx
 * @Date: 2018/12/4 14:10
 * @copyright: 东方微银科技（北京）有限公司
 */
public interface JudgeAnynasisReadDao {

    /**
     * @Description: 客户角色准确性校验 结果映射表
     * @param: [value]
     * @return: String
     * @author:Yxx
     * @Date: 2018/12/5 10:21
     */
    public String judgeRoleCheckMapping(String value);

    /**
     * @Description: 通过案由或者案号获取对应 取值代码和取值文字描述
     * @param: [caseInfo]
     * @return: Map<String, Object>
     * @author:Yxx
     * @Date: 2018/12/5 11:01
     */
    public Map<String, Object> caseReason(String caseInfo);

    /**
     * @Description: 根据优先级获取除优先级最高的审理结果
     * @param: [JudgeResultList]
     * @return: List<Map<String, String>>
     * @author: Yxx
     * @Date: 2018/12/6 9:21
     */
    public List<Map<String, String>> judgeResultFinalMap(List<String> JudgeResultList);

    /**
     * @Description: 案件结果对客户的影响
     * @param: [map]
     * @return: Map<String, Object>
     * @author: Yxx
     * @Date: 2018/12/6 10:46
     */
    public Map<String, Object> resultImpact(Map<String, Object> map);

    /**
     * @Description: 涉案金额 正则基础配置
     * @param: []
     * @return: List<Map<String, Object>>
     * @author: Yxx
     * @Date: 2018/12/7 21:02
     */
    public List<Map<String, Object>> caseMoneyBasic();

    /**
     * @Description: 涉案金额 正则匹配式
     * @param: []
     * @return: List<Map<String, Object>>
     * @author: Yxx
     * @Date: 2018/12/7 21:16
     */
    public List<Map<String, Object>> caseMoneyRegex();

    /**
     * @Description: 审理结果 正则基础表
     * @param: []
     * @return: List<Map<String, Object>>
     * @author: Yxx
     * @Date: 2018/12/5 9:30
     */
    public List<Map<String, Object>> judgeResultBasic();

    /**
     * @Description: 审理结果 正则匹配式
     * @param: []
     * @return:  List<Map<String, Object>>
     * @author: Yxx
     * @Date: 2018/12/5 10:23
     */
    public List<Map<String, Object>> judgeResultRegex();

    /**
     * @Description: 客户角色准确性校验 正则基础表
     * @param: []
     * @return:  List<Map<String, Object>>
     * @author: Yxx
     * @Date: 2018/12/4 11:03
     */
    public List<Map<String, Object>> judgeRoleCheckBasic();

    /**
     * @Description: 客户角色准确性校验 正则匹配式
     * @param: []
     * @return:  List<Map<String, String>>
     * @author: Yxx
     * @Date: 2018/12/4 13:16
     */
    public List<Map<String, String>> judgeRoleCheckRegex();

    /**
     * @Description: 法院等级 正则基础表
     * @param: []
     * @return:  List<Map<String, Object>>
     * @author: Yxx
     * @Date: 2018/12/4 14:06
     */
    public List<Map<String, Object>> courtLevelBasic();

    /**
     * @Description: 法院等级 正则匹配式
     * @param: []
     * @return:  List<Map<String, Object>>
     * @author: Yxx
     * @Date: 2018/12/9 14:06
     */
    public List<Map<String, Object>> courtLevelRegex();

    /**
     * @Description: 获取解析诉讼和结构化数据legal_data_structured表里数据
     * @param: []
     * @return:  List<LegalStructuredData>
     * @author: Yxx
     * @Date: 2018/12/9 14:06
     */
    public List<LegalStructuredData> getLegalStructuredData();

}


