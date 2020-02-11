package com.dfwy.online.sparkstreamingtask.dao;

import common.pojo.quotas.QuotasDataValueEntity;
import common.pojo.quotas.QuotasEntity;
import common.utils.exception.DaoException;

import java.util.List;



public interface IdeQuotasWriteDao{
	//将指标真实值插入指标结果表
	void inserQuotasDataValueList(List<QuotasDataValueEntity> dataValueList) throws DaoException;
	//批量新增指标信息
	void addBatch(List<QuotasEntity> quotasList) throws DaoException;

}
