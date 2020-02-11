package com.dfwy.online.sparkstreamingtask.dao;

import common.pojo.quotas.QuotasEntity;

import java.util.List;
import java.util.Map;


/**
 * @description: 指标接口（读）
 * @author: FangRen
 * @version V2.0 
 * @date: 2018年7月2日 下午4:46:02
 * @copyright©2018东方微银网络信息（北京）有限公司 
 * @fileName:com.ide.mapper.dao.read.IdeQuotasReadDao.java
 */
public interface IdeQuotasReadDao{
	// 执行指标SQL获得真实值
	Map<String,String> execQuotas(Map<String, Object> map);
	// 分页查询指标基础信息
	List<QuotasEntity> find(QuotasEntity quotas);
}
