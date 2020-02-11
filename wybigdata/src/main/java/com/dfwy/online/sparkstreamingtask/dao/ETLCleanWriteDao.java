package com.dfwy.online.sparkstreamingtask.dao;

import com.dfwy.online.sparkstreamingtask.jsondemo.JsonDemoObject;
import common.pojo.etlstandardtable.*;
import common.utils.exception.DaoException;
import java.util.List;


public interface ETLCleanWriteDao {

	//R1103数据源下的表
	void inserjsonDemoObjectList(List<JsonDemoObject> jsonDemoObjectList) throws DaoException;
	void inserStdEntAlterListList(List<StdEntAlterList> StdEntAlterListList) throws DaoException;
	void inserStdEntBasicListList(List<StdEntBasicList> stdEntBasicListList)throws DaoException;
    void inserStdEntLrinvListList(List<StdEntLrinvList> stdEntLrinvListList)throws DaoException;
    void inserStdEntLrpositionListList(List<StdEntLrpositionList> stdEntLrpositionListList)throws DaoException;
	void inserStdEntMorDetailListList(List<StdEntMorDetailList> stdEntMorDetailListList)throws DaoException;
	void inserStdEntPersonListList(List<StdEntPersonList> tdEntPersonListList)throws DaoException;
	void inserStdEntShareHolderListList(List<StdEntShareHolderList> StdEntShareHolderListList)throws DaoException;
	void inserStdEntSharesfrostListList(List<StdEntSharesfrostList> stdEntSharesfrostListList)throws DaoException;
	//工商司法样例数据中，报文为空的表
	void inserStdEntSharesImpawnListList(List<StdEntSharesImpawnList> stdEntSharesImpawnListList)throws DaoException;
	void inserStdEntMorGuainfoListList(List<StdEntMorGuainfoList> stdEntMorGuainfoListList)throws DaoException;
	void inserStdEntLiquidationListList(List<StdEntLiquidationList> stdEntLiquidationListList)throws DaoException;
	void inserStdEntInvitemListList(List<StdEntInvitemList> stdEntInvitemListList)throws DaoException;
	void inserStdEntFiliationListList(List<StdEntFiliationList> stdEntFiliationListList)throws DaoException;
	void inserStdEntCaseInfoListList(List<StdEntCaseInfoList> stdEntCaseInfoListList)throws DaoException;
	/*
	* @Description: 合并下面王飞马八个接口
	* @param: [StdEntRyposlrListList]
	* @return: void
	* @author:Dxx
	* @Date: 2018/12/21 12:00
	*/
	void inserStdEntRyposlrListList(List<StdEntRyposlrList> StdEntRyposlrListList) throws DaoException;
	void inserStdEntRyposperListList(List<StdEntRyposperList> stdEntRyposperList) throws DaoException;
	void inserStdEntRyposshaListList(List<StdEntRyposshaList> stdEntRyposshaList) throws DaoException;
	void inserstdLegalEnterpriseExecutedList(List<StdLegalEnterpriseExecuted> stdLegalEnterpriseExecuted) throws DaoException;
	void inserStdLegalIndUnexecutedList(List<StdLegalIndUnexecuted> stdLegalIndUnexecuted) throws DaoException;
	void inserStdLegalIndividualExecutedList(List<StdLegalIndividualExecuted> stdLegalIndividualExecuted) throws DaoException;
	void inserstdLegalEntUnexecutedList(List<StdLegalEntUnexecuted> stdLegalEntUnexecuted) throws DaoException;
	void inserStdLegalDataStructuredList(List<StdLegalDataStructured> stdLegalDataStructured) throws DaoException;

}


