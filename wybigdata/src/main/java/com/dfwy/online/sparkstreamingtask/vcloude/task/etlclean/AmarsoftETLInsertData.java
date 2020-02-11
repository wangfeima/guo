package com.dfwy.online.sparkstreamingtask.vcloude.task.etlclean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao;
import common.pojo.applicantinformation.ApplicantInformationTO;
import common.pojo.etlstandardtable.*;
import common.utils.date.DateUtils;

import common.utils.etlclean.EtlCleanRules;
import common.utils.exception.ErrorCodeIDE;
import common.utils.exception.ServiceException;
import common.utils.sqlsessionfactoryutil.SqlSessionFactoryUtil;
import common.utils.uuid.UUIDGenerator;
import org.apache.ibatis.session.SqlSession;
import scala.collection.immutable.Range;

import java.util.ArrayList;
import java.util.List;


/**
 * @author: dxx  amarsoftData
 * @version: V-Cloude
 * @date: 2018/12/17 13:41
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.vcloude.task
 * @description:对报文数据进行数据清洗工作，最后传入mysql标准表！
 */
public class AmarsoftETLInsertData {
    public static void etlClean(ApplicantInformationTO applicantInformationTO) {
        //将传输过来的报文数据封装进申请人实例中，做数据的传输！
        applicantInformationTO.setDataSourceFrom("amarsoftData");
        etlCleanStdEntAlterList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntBasicList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntCaseInfoList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntFiliationList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntInvitemList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntLiquidationList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntLrinvList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntLrpositionList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntMorDetailList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntMorGuainfoList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntPersonList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntShareHolderList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntSharesImpawnList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntSharesfrostList(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());

        etlCleanStdEntR1104(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalEnterpriseExecutedR228(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalIndUnexecutedR231(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalIndividualExecutedR229(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalEntUnexecutedR230(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalDataStructuredR227(applicantInformationTO.getAmarsoftData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 18:04
    */
    public static void etlCleanStdEntSharesImpawnList(String amarsoftData, String businessID, String reqID,String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray sharesImpawnList = r1103Data.getJSONObject(0).getJSONArray("sharesImpawnList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (sharesImpawnList.size() > 0) {
            List<StdEntSharesImpawnList> stdEntSharesImpawnListList = new ArrayList<>();
            //建立入库对象数组
            StdEntSharesImpawnList stdEntSharesImpawnList = null;
            for (Object sharesImpawnListObj : sharesImpawnList) {
                stdEntSharesImpawnList = new StdEntSharesImpawnList();
                JSONObject sharesImpawnListObject = (JSONObject) sharesImpawnListObj;
                stdEntSharesImpawnList.setUuID(UUIDGenerator.generate());
                stdEntSharesImpawnList.setBusinessID(businessID);
                stdEntSharesImpawnList.setReqID(reqID);
                stdEntSharesImpawnList.setReqID(reqID);
                stdEntSharesImpawnList.setDatasourcefrom(dataSourceFrom);

                stdEntSharesImpawnList.setImporg(sharesImpawnListObject.getString("impoRg"));
                stdEntSharesImpawnList.setImporgtype(sharesImpawnListObject.getString("impoRgtype"));
                stdEntSharesImpawnList.setImpam(sharesImpawnListObject.getDouble("impAm"));
                stdEntSharesImpawnList.setImponrecdate(DateUtils.strToDate(sharesImpawnListObject.getString("imponrecDate")));
                stdEntSharesImpawnList.setImpexaeep(sharesImpawnListObject.getString("impExaeep"));
                stdEntSharesImpawnList.setImpsandate(DateUtils.strToDate(sharesImpawnListObject.getString("impSanDate")));
                stdEntSharesImpawnList.setImpto(DateUtils.strToDate(sharesImpawnListObject.getString("impTo")));

                stdEntSharesImpawnListList.add(stdEntSharesImpawnList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntSharesImpawnListList(stdEntSharesImpawnListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_shares_impawn_list标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntSharesImpawnListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 18:04
    */
    public static void etlCleanStdEntMorGuainfoList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray morguaInfoList = r1103Data.getJSONObject(0).getJSONArray("morguaInfoList");
        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (morguaInfoList.size() > 0) {
            List<StdEntMorGuainfoList> stdEntMorGuainfoListList = new ArrayList<>();
            //建立入库对象数组
            StdEntMorGuainfoList stdEntMorGuainfoList = null;
            for (Object morguaInfoListObj : morguaInfoList) {
                stdEntMorGuainfoList = new StdEntMorGuainfoList();
                JSONObject morguaInfoListObject = (JSONObject) morguaInfoListObj;
                stdEntMorGuainfoList.setUuID(UUIDGenerator.generate());
                stdEntMorGuainfoList.setBusinessID(businessID);
                stdEntMorGuainfoList.setReqID(reqID);
                stdEntMorGuainfoList.setDatasourcefrom(dataSourceFrom);

                //stdEntMorGuainfoList.setQuan(morguaInfoListObject.getString("quan")); 12,15,16,18,17,7
                String quan = EtlCleanRules.deleteAllSpace(morguaInfoListObject.getString("quan"));
                String s = EtlCleanRules.deleteMinus(quan);
                String s1 = EtlCleanRules.deletePlus(s);
                String s2 = EtlCleanRules.deleteSpecialCharacters(s1);
                String s3 = EtlCleanRules.deleteZero(s2);
                String numeric = EtlCleanRules.isNumeric(s3);
                stdEntMorGuainfoList.setQuan(numeric);

                stdEntMorGuainfoList.setMorregID(morguaInfoListObject.getString("morreg_Id"));
                stdEntMorGuainfoList.setGuaname(morguaInfoListObject.getString("guaName"));
                stdEntMorGuainfoList.setGuavalue(morguaInfoListObject.getDouble("value"));

                stdEntMorGuainfoListList.add(stdEntMorGuainfoList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntMorGuainfoListList(stdEntMorGuainfoListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_mor_guainfo_list标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntMorGuainfoListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 18:03
    */
    public static void etlCleanStdEntLiquidationList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray liquidationList = r1103Data.getJSONObject(0).getJSONArray("liquidationList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (liquidationList.size() > 0) {
            List<StdEntLiquidationList> stdEntLiquidationListList = new ArrayList<>();
            //建立入库对象数组
            StdEntLiquidationList stdEntLiquidationList = null;
            for (Object liquidationListObj : liquidationList) {
                stdEntLiquidationList = new StdEntLiquidationList();
                JSONObject liquidationListObject = (JSONObject) liquidationListObj;
                stdEntLiquidationList.setUuID(UUIDGenerator.generate());
                stdEntLiquidationList.setBusinessID(businessID);
                stdEntLiquidationList.setReqID(reqID);
                stdEntLiquidationList.setDatasourcefrom(dataSourceFrom);

                stdEntLiquidationList.setLigentity(liquidationListObject.getString("ligentity"));
                stdEntLiquidationList.setLigprincipal(liquidationListObject.getString("ligprincipal"));
                stdEntLiquidationList.setLiqmen(liquidationListObject.getString("liqMen"));
                stdEntLiquidationList.setLigst(liquidationListObject.getString("liGst"));
                stdEntLiquidationList.setLigenddate(DateUtils.strToDate(liquidationListObject.getString("ligEndDate")));
                stdEntLiquidationList.setDebttranee(liquidationListObject.getString("debtTranee"));
                stdEntLiquidationList.setClaimtranee(liquidationListObject.getString("claimTranee"));

                stdEntLiquidationListList.add(stdEntLiquidationList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntLiquidationListList(stdEntLiquidationListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_liquidation_list标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntLiquidationListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 18:03
    */
    public static void etlCleanStdEntInvitemList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray entinvItemList = r1103Data.getJSONObject(0).getJSONArray("entinvItemList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (entinvItemList.size() > 0) {
            List<StdEntInvitemList> stdEntInvitemListList = new ArrayList<>();
            //建立入库对象数组
            StdEntInvitemList stdEntInvitemList = null;
            for (Object entinvItemListObj : entinvItemList) {
                stdEntInvitemList = new StdEntInvitemList();
                JSONObject entinvItemListObject = (JSONObject) entinvItemListObj;
                stdEntInvitemList.setUuID(UUIDGenerator.generate());
                stdEntInvitemList.setBusinessID(businessID);
                stdEntInvitemList.setReqID(reqID);
                stdEntInvitemList.setDatasourcefrom(dataSourceFrom);

                stdEntInvitemList.setInventname(entinvItemListObject.getString("entName"));
                stdEntInvitemList.setInvregno(entinvItemListObject.getString("regNo"));
                stdEntInvitemList.setInventtype(entinvItemListObject.getString("entType"));

                String regCap = EtlCleanRules.deleteZero(
                        EtlCleanRules.deleteSpecialCharacters(
                                EtlCleanRules.deletePlus(
                                        EtlCleanRules.amountDeleteComma(
                                                entinvItemListObject.getString("regCap")))));
                stdEntInvitemList.setInvregcap(regCap);

                stdEntInvitemList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvItemListObject.getString("canDate"))));
                stdEntInvitemList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvItemListObject.getString("revDate"))));
                stdEntInvitemList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvItemListObject.getString("esDate"))));

                String subConam = EtlCleanRules.amountDeleteComma(entinvItemListObject.getString("subConam"));
                String s0 = EtlCleanRules.deletePlus(subConam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntInvitemList.setSubconam(Double.parseDouble(s2));

                stdEntInvitemList.setFundedratio(
                        String.valueOf(
                                EtlCleanRules.deletePercentSign(
                                        entinvItemListObject.getString("fundedRatio"))));

                stdEntInvitemList.setRegcapcur(entinvItemListObject.getString("regCapcur"));
                stdEntInvitemList.setRegstatus(entinvItemListObject.getString("entStatus"));
                stdEntInvitemList.setRegorg(entinvItemListObject.getString("regOrg"));
                stdEntInvitemList.setCurrency(entinvItemListObject.getString("currency"));
                stdEntInvitemList.setInvlrname(entinvItemListObject.getString("name"));

                stdEntInvitemListList.add(stdEntInvitemList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntInvitemListList(stdEntInvitemListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_invitem_list标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntInvitemListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 15:08
    */
    public static void etlCleanStdEntFiliationList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray filiationList = r1103Data.getJSONObject(0).getJSONArray("filiationList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (filiationList.size() > 0) {
            List<StdEntFiliationList> stdEntFiliationListList = new ArrayList<>();
            //建立入库对象数组
            StdEntFiliationList stdEntFiliationList = null;
            for (Object filiationListObj : filiationList) {
                stdEntFiliationList = new StdEntFiliationList();
                JSONObject filiationListObject = (JSONObject) filiationListObj;
                stdEntFiliationList.setUuID(UUIDGenerator.generate());
                stdEntFiliationList.setBusinessID(businessID);
                stdEntFiliationList.setReqID(reqID);
                stdEntFiliationList.setDatasourcefrom(dataSourceFrom);

                stdEntFiliationList.setBrname(filiationListObject.getString("brName"));
                stdEntFiliationList.setBrregno(filiationListObject.getString("brRegno"));
                stdEntFiliationList.setBrprincipal(filiationListObject.getString("brPrincipal"));
                stdEntFiliationList.setCbuitem(filiationListObject.getString("cbuItem"));
                stdEntFiliationList.setBraddr(filiationListObject.getString("brAddr"));

                stdEntFiliationListList.add(stdEntFiliationList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntFiliationListList(stdEntFiliationListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_filiation_list标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntFiliationListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 14:48
    */
    public static void etlCleanStdEntCaseInfoList(String amarsoftData, String businessID, String reqID,String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray caseInfoList = r1103Data.getJSONObject(0).getJSONArray("caseInfoList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (caseInfoList.size() > 0) {
            List<StdEntCaseInfoList> stdEntCaseInfoListList = new ArrayList<>();
            //建立入库对象数组
            StdEntCaseInfoList stdEntCaseInfoList = null;
            for (Object caseInfoListObj : caseInfoList) {
                stdEntCaseInfoList = new StdEntCaseInfoList();
                JSONObject caseInfoListObject = (JSONObject) caseInfoListObj;
                stdEntCaseInfoList.setUuID(UUIDGenerator.generate());
                stdEntCaseInfoList.setBusinessID(businessID);
                stdEntCaseInfoList.setReqID(reqID);
                stdEntCaseInfoList.setDatasourcefrom(dataSourceFrom);

                //stdEntCaseInfoList.setCasetime(caseInfoListObject.getString("caseTime"));
                //stdEntCaseInfoList.setPendecissdate(caseInfoListObject.getString("pendecissDate"));
                stdEntCaseInfoList.setCasetime(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(caseInfoListObject.getString("caseTime"))));
                stdEntCaseInfoList.setPendecissdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(caseInfoListObject.getString("pendecissDate"))));
                //stdEntCaseInfoList.setPenam(caseInfoListObject.getString("penAm"));
                String penAm = EtlCleanRules.amountDeleteComma(caseInfoListObject.getString("penAm"));
                String s0 = EtlCleanRules.deletePlus(penAm);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntCaseInfoList.setPenam(s2);

                stdEntCaseInfoList.setCasereason(caseInfoListObject.getString("caseReason"));
                stdEntCaseInfoList.setCasetype(caseInfoListObject.getString("caseType"));
                stdEntCaseInfoList.setExesort(caseInfoListObject.getString("exeSort"));
                stdEntCaseInfoList.setCaseresult(caseInfoListObject.getString("caseResult"));
                stdEntCaseInfoList.setPenauth(caseInfoListObject.getString("penAuth"));
                stdEntCaseInfoList.setIllegfact(caseInfoListObject.getString("illegFact"));
                stdEntCaseInfoList.setPenbasis(caseInfoListObject.getString("penBasis"));
                stdEntCaseInfoList.setPentype(caseInfoListObject.getString("penType"));
                stdEntCaseInfoList.setPenresult(caseInfoListObject.getString("penResult"));
                stdEntCaseInfoList.setPenexest(caseInfoListObject.getString("penExest"));
                //stdEntCaseInfoList.setPendecno(caseInfoListObject.getString("Pendecno"));原始JSON报文没有此条数据

                stdEntCaseInfoListList.add(stdEntCaseInfoList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntCaseInfoListList(stdEntCaseInfoListList);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                          + ",std_ent_caseinfo_list标准表入库失败！"
                          + System.getProperty("line.separator")
                          , e);*/
            } finally {
                stdEntCaseInfoListList.clear();
                sqlSession.close();
            }
        }
    }


    public static void etlCleanStdEntSharesfrostList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray sharesFrostList = r1103Data.getJSONObject(0).getJSONArray("sharesFrostList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (sharesFrostList.size() > 0) {
            List<StdEntSharesfrostList> stdEntSharesfrostListList = new ArrayList<>();
            //建立入库对象数组
            StdEntSharesfrostList stdEntSharesfrostList = null;
            for (Object sharesFrostListObj : sharesFrostList) {
                stdEntSharesfrostList = new StdEntSharesfrostList();
                JSONObject sharesFrostListObject = (JSONObject) sharesFrostListObj;
                stdEntSharesfrostList.setUuID(UUIDGenerator.generate());
                stdEntSharesfrostList.setBusinessID(businessID);
                stdEntSharesfrostList.setReqID(reqID);
                stdEntSharesfrostList.setDatasourcefrom(dataSourceFrom);

                stdEntSharesfrostList.setFrodocno(sharesFrostListObject.getString("froDocno"));
                stdEntSharesfrostList.setFroauth(sharesFrostListObject.getString("froAuth"));
                stdEntSharesfrostList.setFrofrom(DateUtils.strToDate(sharesFrostListObject.getString("froFrom")));
                stdEntSharesfrostList.setFroto(DateUtils.strToDate(sharesFrostListObject.getString("froTo")));
                stdEntSharesfrostList.setFroam(sharesFrostListObject.getDouble("froAm"));
                stdEntSharesfrostList.setThawauth(sharesFrostListObject.getString("thawAuth"));
                stdEntSharesfrostList.setThawdocno(sharesFrostListObject.getString("thawDocno"));
                stdEntSharesfrostList.setThawdate(DateUtils.strToDate(sharesFrostListObject.getString("thawDate")));
                stdEntSharesfrostList.setThawcomment(sharesFrostListObject.getString("thawComment"));

                stdEntSharesfrostListList.add(stdEntSharesfrostList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntSharesfrostListList(stdEntSharesfrostListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                            + ",std_ent_sharesfrost_list标准表入库失败！"
                            + System.getProperty("line.separator")
                            , e);*/
            } finally {
                stdEntSharesfrostListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 11:14
    */
    public static void etlCleanStdEntShareHolderList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray shareHolderList = r1103Data.getJSONObject(0).getJSONArray("shareHolderList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (shareHolderList.size() > 0) {
            List<StdEntShareHolderList> stdEntShareHolderListList = new ArrayList<>();
            //建立入库对象数组
            StdEntShareHolderList stdEntShareHolderList = null;
            for (Object shareHolderObj : shareHolderList) {
                stdEntShareHolderList = new StdEntShareHolderList();
                JSONObject shareHolderObject = (JSONObject) shareHolderObj;
                stdEntShareHolderList.setUuID(UUIDGenerator.generate());
                stdEntShareHolderList.setBusinessID(businessID);
                stdEntShareHolderList.setReqID(reqID);
                stdEntShareHolderList.setDatasourcefrom(dataSourceFrom);

                stdEntShareHolderList.setFundedratio(String.valueOf(EtlCleanRules.deletePercentSign(shareHolderObject.getString("fundedRatio"))));
                stdEntShareHolderList.setCondate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(shareHolderObject.getString("conDate"))));
                String subConam = EtlCleanRules.amountDeleteComma(shareHolderObject.getString("subConam"));
                String s0 = EtlCleanRules.deletePlus(subConam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntShareHolderList.setSubconam(Double.parseDouble(s2));

                stdEntShareHolderList.setShareholdername(shareHolderObject.getString("shareholderName"));
                stdEntShareHolderList.setRegcapcur(shareHolderObject.getString("regCapCur"));
                stdEntShareHolderList.setCountry(shareHolderObject.getString("country"));

                stdEntShareHolderListList.add(stdEntShareHolderList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntShareHolderListList(stdEntShareHolderListList);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                            + ",std_ent_share_holder_list标准表入库失败！"
                            + System.getProperty("line.separator")
                            , e);
            } finally {
                stdEntShareHolderListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 11:14
    */
    public static void etlCleanStdEntPersonList(String amarsoftData, String businessID, String reqID,String dataSourceFrom){
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray personList = r1103Data.getJSONObject(0).getJSONArray("personList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (personList.size() > 0) {
            List<StdEntPersonList> stdEntPersonListList = new ArrayList<>();
            //建立入库对象数组
            StdEntPersonList stdEntPersonList = null;
            for (Object personObj : personList) {
                stdEntPersonList = new StdEntPersonList();
                JSONObject personObject = (JSONObject) personObj;
                stdEntPersonList.setUuID(UUIDGenerator.generate());
                stdEntPersonList.setBusinessID(businessID);
                stdEntPersonList.setReqID(reqID);
                stdEntPersonList.setDatasourcefrom(dataSourceFrom);

                stdEntPersonList.setName(personObject.getString("name"));
                stdEntPersonList.setPosition(personObject.getString("position"));
                stdEntPersonList.setSex(personObject.getString("sex"));

                stdEntPersonListList.add(stdEntPersonList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntPersonListList(stdEntPersonListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                            + ",std_ent_person_list标准表入库失败！"
                            + System.getProperty("line.separator")
                            , e);*/
            } finally {
                stdEntPersonListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 11:14
    */
    public static void etlCleanStdEntMorDetailList(String amarsoftData, String businessID, String reqID,String dataSourceFrom){
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray morDetailList = r1103Data.getJSONObject(0).getJSONArray("morDetailList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (morDetailList.size() > 0) {
            List<StdEntMorDetailList> stdEntMorDetailListList = new ArrayList<>();
            //建立入库对象数组
            StdEntMorDetailList stdEntMorDetailList = null;
            for (Object morDetailObj : morDetailList) {
                stdEntMorDetailList = new StdEntMorDetailList();
                JSONObject morDetailObject = (JSONObject) morDetailObj;
                stdEntMorDetailList.setUuID(UUIDGenerator.generate());
                stdEntMorDetailList.setBusinessID(businessID);
                stdEntMorDetailList.setReqID(reqID);
                stdEntMorDetailList.setDatasourcefrom(dataSourceFrom);

                stdEntMorDetailList.setMorregid(morDetailObject.getString("morregId"));
                stdEntMorDetailList.setMortgagor(morDetailObject.getString("mortgaGor"));
                stdEntMorDetailList.setMore(morDetailObject.getString("more"));
                stdEntMorDetailList.setRegorg(morDetailObject.getString("regOrg"));
                stdEntMorDetailList.setRegdate(DateUtils.strToDate(morDetailObject.getString("regiDate")));
                stdEntMorDetailList.setMortype(morDetailObject.getString("morType"));
                stdEntMorDetailList.setMorregcno(morDetailObject.getString("morRegcno"));
                stdEntMorDetailList.setAppregrea(morDetailObject.getString("appreGrea"));
                stdEntMorDetailList.setPriclaseckind(morDetailObject.getString("priclasecKind"));
                stdEntMorDetailList.setPriclasecam(morDetailObject.getDouble("priclaseCam"));
                stdEntMorDetailList.setPefperfrom(DateUtils.strToDate(morDetailObject.getString("pefperFrom")));
                stdEntMorDetailList.setPefperto(DateUtils.strToDate(morDetailObject.getString("pefperTo")));
                stdEntMorDetailList.setCandate(DateUtils.strToDate(morDetailObject.getString("canDate")));

                stdEntMorDetailListList.add(stdEntMorDetailList);
            }


            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntMorDetailListList(stdEntMorDetailListList);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                             + ",std_ent_mor_detail_list标准表入库失败！"
                             + System.getProperty("line.separator")
                             , e);*/
            } finally {
                stdEntMorDetailListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 9:54
    */
    public static void etlCleanStdEntLrpositionList(String amarsoftData, String businessID, String reqID,String dataSourceFrom){
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray frPositionList = r1103Data.getJSONObject(0).getJSONArray("frPositionList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (frPositionList.size() > 0) {
            List<StdEntLrpositionList> stdEntLrpositionListList = new ArrayList<>();

            //建立入库对象数组
            StdEntLrpositionList stdEntLrpositionList = null;
            for (Object frPositionObj : frPositionList) {
                stdEntLrpositionList = new StdEntLrpositionList();
                JSONObject frPositionObject = (JSONObject) frPositionObj;
                stdEntLrpositionList.setUuID(UUIDGenerator.generate());
                stdEntLrpositionList.setBusinessID(businessID);
                stdEntLrpositionList.setReqID(reqID);
                stdEntLrpositionList.setDatasourcefrom(dataSourceFrom);

                /*stdEntLrpositionList.setCandate(frPositionObject.getString("canDate"));
                stdEntLrpositionList.setRevdate(frPositionObject.getString("revDate"));
                stdEntLrpositionList.setEsdate(frPositionObject.getString("esDate"));*/
                stdEntLrpositionList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frPositionObject.getString("cancelDate"))));
                stdEntLrpositionList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frPositionObject.getString("revokeDate"))));
                stdEntLrpositionList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frPositionObject.getString("esDate"))));

                stdEntLrpositionList.setLrname(frPositionObject.getString("frName"));
                stdEntLrpositionList.setPostionentname(frPositionObject.getString("entName"));
                stdEntLrpositionList.setPostioncreditcode(frPositionObject.getString("creditCode"));
                stdEntLrpositionList.setPostionregno(frPositionObject.getString("regNo"));
                stdEntLrpositionList.setPostionenttype(frPositionObject.getString("entType"));
                stdEntLrpositionList.setPostionregcap(frPositionObject.getDouble("regCap"));
                stdEntLrpositionList.setRegcapcur(frPositionObject.getString("regCapCur"));
                stdEntLrpositionList.setRegstatus(frPositionObject.getString("entStatus"));
                stdEntLrpositionList.setRegorg(frPositionObject.getString("regOrg"));
                stdEntLrpositionList.setPosition(frPositionObject.getString("position"));
                stdEntLrpositionList.setLerepsign(frPositionObject.getString("lerepsign"));

                stdEntLrpositionListList.add(stdEntLrpositionList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntLrpositionListList(stdEntLrpositionListList);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                            + ",std_ent_lrposition_list标准表入库失败！"
                            + System.getProperty("line.separator")
                            , e);*/
            } finally {
                stdEntLrpositionListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/19 9:54
    */
    public static void etlCleanStdEntLrinvList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray frinvList = r1103Data.getJSONObject(0).getJSONArray("frinvList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (frinvList.size() > 0) {
            List<StdEntLrinvList> stdEntLrinvListList = new ArrayList<>();
            //建立入库对象数组
            StdEntLrinvList stdEntLrinvList = null;
            for (Object frinvObj : frinvList) {
                stdEntLrinvList = new StdEntLrinvList();
                JSONObject frinvObject = (JSONObject) frinvObj;
                stdEntLrinvList.setUuID(UUIDGenerator.generate());
                stdEntLrinvList.setBusinessID(businessID);
                stdEntLrinvList.setReqID(reqID);
                stdEntLrinvList.setDatasourcefrom(dataSourceFrom);

                //stdEntLrinvList.setFundedratio(frinvObject.getString("fundedRatio"));
                stdEntLrinvList.setFundedratio(
                        String.valueOf(
                                EtlCleanRules.deletePercentSign(
                                        frinvObject.getString("fundedRatio"))));
                //stdEntLrinvList.setInvregcap(frinvObject.getString("regCap"));
                String regCap = EtlCleanRules.deleteZero(
                        EtlCleanRules.deleteSpecialCharacters(
                                EtlCleanRules.deletePlus(
                                        EtlCleanRules.amountDeleteComma(
                                                frinvObject.getString("regCap")))));
                stdEntLrinvList.setInvregcap(regCap);
                //stdEntLrinvList.setSubconam(frinvObject.getDouble("subConam"));
                String subConam = EtlCleanRules.amountDeleteComma(frinvObject.getString("subConam"));
                String s0 = EtlCleanRules.deletePlus(subConam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntLrinvList.setSubconam(Double.parseDouble(s2));
                /*stdEntLrinvList.setCandate(frinvObject.getString("canDate"));
                stdEntLrinvList.setRevdate(frinvObject.getString("revDate"));
                stdEntLrinvList.setEsdate(frinvObject.getString("esDate"));*/
                stdEntLrinvList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvObject.getString("cancelDate"))));
                stdEntLrinvList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvObject.getString("revokeDate"))));
                stdEntLrinvList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvObject.getString("esDate"))));

                stdEntLrinvList.setLrname(frinvObject.getString("frName"));
                stdEntLrinvList.setInventname(frinvObject.getString("entName"));
                stdEntLrinvList.setInventtype(frinvObject.getString("entType"));
                stdEntLrinvList.setInvregno(frinvObject.getString("regNo"));
                stdEntLrinvList.setRegcapcur(frinvObject.getString("regCapCur"));
                stdEntLrinvList.setRegorg(frinvObject.getString("regOrg"));
                stdEntLrinvList.setRegstatus(frinvObject.getString("entStatus"));
                stdEntLrinvList.setCurrency(frinvObject.getString("currency"));

                stdEntLrinvListList.add(stdEntLrinvList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntLrinvListList(stdEntLrinvListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                             + ",std_ent_lrinv_list标准表入库失败！"
                             + System.getProperty("line.separator")
                             , e);*/
            } finally {
                stdEntLrinvListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/18 19:41
    */
    public static void etlCleanStdEntBasicList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray basicList = r1103Data.getJSONObject(0).getJSONArray("basicList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (basicList.size() > 0) {
            List<StdEntBasicList> stdEntBasicListList = new ArrayList<>();
            //建立入库对象数组
            StdEntBasicList stdEntBasicList = null;
            for (Object basicObj : basicList) {
                stdEntBasicList = new StdEntBasicList();
                JSONObject basicObject = (JSONObject) basicObj;

                stdEntBasicList.setUuID(UUIDGenerator.generate());
                stdEntBasicList.setBusinessID(businessID);
                stdEntBasicList.setReqID(reqID);
                stdEntBasicList.setDatasourcefrom(dataSourceFrom);

                stdEntBasicList.setEntname(basicObject.getString("enterpriseName"));
                stdEntBasicList.setCreditcode(basicObject.getString("creditCode"));
                //stdEntBasicList.setOrgcode(basicObject.getString("orgCode"));Json没有此字段
                stdEntBasicList.setRegno(basicObject.getString("regNo"));
                stdEntBasicList.setEnttype(basicObject.getString("enterpriseType"));
                stdEntBasicList.setLrname(basicObject.getString("frName"));
                stdEntBasicList.setRegcap(basicObject.getDouble("regCap"));
                stdEntBasicList.setRegcapcur(basicObject.getString("regCapCur"));
                stdEntBasicList.setReccap(basicObject.getDouble("recCap"));
                stdEntBasicList.setRegorg(basicObject.getString("regOrg"));
                stdEntBasicList.setRegstatus(basicObject.getString("enterpriseStatus"));
                stdEntBasicList.setAddress(basicObject.getString("address"));
                stdEntBasicList.setAbuitem(basicObject.getString("abuItem"));
                stdEntBasicList.setCbuitem(basicObject.getString("cbuItem"));
                stdEntBasicList.setOperatescope(basicObject.getString("operateScope"));
                stdEntBasicList.setOperatescopeandform(basicObject.getString("operateScopeAndForm"));
                stdEntBasicList.setIndustryphycode(basicObject.getString("industryPhyCode"));
                stdEntBasicList.setIndustryphyname(basicObject.getString("industryPhyName"));
                stdEntBasicList.setIndustrycode(basicObject.getString("industryCode"));
                stdEntBasicList.setIndustryname(basicObject.getString("industryName"));
                stdEntBasicList.setOriregno(basicObject.getString("oriRegNo"));
                stdEntBasicList.setIdentitycardnt(basicObject.getString("identityCardNt"));

                stdEntBasicList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("esDate"))));
                stdEntBasicList.setOpenfrom(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("openFrom"))));
                stdEntBasicList.setOpento(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("openTo"))));
                stdEntBasicList.setApprdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("apprDate"))));
                stdEntBasicList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("cancelDate"))));
                stdEntBasicList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("revokeDate"))));
                stdEntBasicList.setAncheyear(EtlCleanRules.dateFormatCleaning(basicObject.getString("ancheYear")));
                stdEntBasicList.setAnchedate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("ancheDate"))));

                stdEntBasicListList.add(stdEntBasicList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntBasicListList(stdEntBasicListList);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                            + ",std_ent_basic_list标准表入库失败！"
                            + System.getProperty("line.separator")
                            , e);*/
            } finally {
                stdEntBasicListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @description: std_ent_alter_list
    * @param: [amarsoftdata, businessid, reqid]
    * @return: void
    * @author:dxx
    * @date: 2018/12/17 19:26
    */
    public static void etlCleanStdEntAlterList(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1103 = stringJsonObject.getJSONObject("R1103");
        JSONArray r1103Data = r1103.getJSONArray("data");
        JSONArray alterList = r1103Data.getJSONObject(0).getJSONArray("alterList");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (alterList.size() > 0) {
            List<StdEntAlterList> StdEntAlterListList = new ArrayList<>();
            StdEntAlterList stdEntAlterList = null;
            for (Object alterObj : alterList) {
                stdEntAlterList = new StdEntAlterList();
                JSONObject alterObject = (JSONObject) alterObj;
                stdEntAlterList.setUuID(UUIDGenerator.generate());
                stdEntAlterList.setBusinessID(businessID);
                stdEntAlterList.setReqID(reqID);
                stdEntAlterList.setDatasourcefrom(dataSourceFrom);

                stdEntAlterList.setAltaf(alterObject.getString("altAf"));
                stdEntAlterList.setAltbe(alterObject.getString("altBe"));
                stdEntAlterList.setAltdate(DateUtils.strToDate(alterObject.getString("altDate")));
                stdEntAlterList.setAltitem(alterObject.getString("altItem"));
                StdEntAlterListList.add(stdEntAlterList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntAlterListList(StdEntAlterListList);
                sqlSession.commit();
            } catch (Exception e) {
                sqlSession.rollback();
                e.printStackTrace();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                            + ",std_ent_alter_list标准表入库失败！"
                            + System.getProperty("line.separator")
                            , e);*/
            } finally {
                StdEntAlterListList.clear();
                sqlSession.close();
            }
        }
    }


    /*
    * @Description: std_ent_ryposlr_list std_ent_ryposper_list std_ent_rypossha_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/17 19:26
    */
    public static void etlCleanStdEntR1104(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONObject r1104 = stringJsonObject.getJSONObject("R1104");
        JSONArray r1104Data = r1104.getJSONArray("data");

        //R1104 表ryPosFrList
        JSONArray ryposlrList = r1104Data.getJSONObject(0).getJSONArray("ryPosFrList");
        List<StdEntRyposlrList> stdEntRyposlrListList = new ArrayList<>();
        StdEntRyposlrList stdEntRyposlrList = null;
        for (Object alterObj : ryposlrList) {
            stdEntRyposlrList = new StdEntRyposlrList();
            JSONObject alterObject = (JSONObject) alterObj;
            stdEntRyposlrList.setUuID(UUIDGenerator.generate());
            stdEntRyposlrList.setBusinessID(businessID);
            stdEntRyposlrList.setReqID(reqID);
            stdEntRyposlrList.setDatasourcefrom(dataSourceFrom);

            stdEntRyposlrList.setEntName(alterObject.getString("entName"));
            stdEntRyposlrList.setEntStatus(alterObject.getString("entStatus"));
            stdEntRyposlrList.setEntType(alterObject.getString("entType"));
            stdEntRyposlrList.setRegCap(alterObject.getString("regCap"));
            stdEntRyposlrList.setRegCapcur(alterObject.getString("regCapcur"));
            stdEntRyposlrList.setRegNo(alterObject.getString("regNo"));
            stdEntRyposlrList.setRyName(alterObject.getString("ryName"));
            stdEntRyposlrListList.add(stdEntRyposlrList);
        }


        //R1104 表 std_ent_ryposper_list
        JSONArray ryPosPerList = r1104Data.getJSONObject(0).getJSONArray("ryPosPerList");
        List<StdEntRyposperList> stdEntRyposperListList = new ArrayList<>();
        StdEntRyposperList stdEntRyposperList = null;
        for (Object alterObj : ryPosPerList) {
            stdEntRyposperList = new StdEntRyposperList();
            JSONObject alterObject = (JSONObject) alterObj;
            stdEntRyposperList.setUuID(UUIDGenerator.generate());
            stdEntRyposperList.setBusinessID(businessID);
            stdEntRyposperList.setReqID(reqID);
            stdEntRyposperList.setDatasourcefrom(dataSourceFrom);

            stdEntRyposperList.setEntname(alterObject.getString("entName"));
            stdEntRyposperList.setRegstatus(alterObject.getString("entStatus"));
            stdEntRyposperList.setEnttype(alterObject.getString("entType"));
            stdEntRyposperList.setPosition(alterObject.getString("position"));
            stdEntRyposperList.setRegcap(alterObject.getString("regCap"));
            stdEntRyposperList.setRegcapcur(alterObject.getString("regCapcur"));
            stdEntRyposperList.setRegno(alterObject.getString("regNo"));
            stdEntRyposperList.setRyname(alterObject.getString("ryName"));
            stdEntRyposperListList.add(stdEntRyposperList);
        }

        //R1104 表 stdEntRyposshaList
        JSONArray ryPosShaList = r1104Data.getJSONObject(0).getJSONArray("ryPosShaList");

        List<StdEntRyposshaList> stdEntRyposshaListList = new ArrayList<>();
        StdEntRyposshaList stdEntRyposshaList = null;
        for (Object alterObj : ryPosShaList) {
            stdEntRyposshaList = new StdEntRyposshaList();
            JSONObject alterObject = (JSONObject) alterObj;
            stdEntRyposshaList.setUuID(UUIDGenerator.generate());
            stdEntRyposshaList.setBusinessID(businessID);
            stdEntRyposshaList.setReqID(reqID);
            stdEntRyposshaList.setDatasourcefrom(dataSourceFrom);

            stdEntRyposshaList.setEntName(alterObject.getString("entName"));
            stdEntRyposshaList.setEntStatus(alterObject.getString("entStatus"));
            stdEntRyposshaList.setEntType(alterObject.getString("entType"));
            stdEntRyposshaList.setRegCap(alterObject.getString("regCap"));
            stdEntRyposshaList.setRegCapcur(alterObject.getString("regCapcur"));
            stdEntRyposshaList.setRegNo(alterObject.getString("regNo"));
            stdEntRyposshaList.setRyName(alterObject.getString("ryName"));
            stdEntRyposshaList.setSubConam(alterObject.getString("subConam"));
            stdEntRyposshaList.setSubCurrency(alterObject.getString("subCurrency"));
            stdEntRyposshaListList.add(stdEntRyposshaList);
        }


        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
        try {
            eTLCleanWriteDao.inserStdEntRyposlrListList(stdEntRyposlrListList);
            eTLCleanWriteDao.inserStdEntRyposperListList(stdEntRyposperListList);
            eTLCleanWriteDao.inserStdEntRyposshaListList(stdEntRyposshaListList);
            sqlSession.commit();
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                    + ",std_ent_ryposlr_list std_ent_ryposper_list std_ent_rypossha_list标准表入库失败！"
                    + System.getProperty("line.separator")
                    , e);*/
        } finally {
            stdEntRyposlrListList.clear();
            stdEntRyposperListList.clear();
            stdEntRyposshaListList.clear();
            sqlSession.close();
        }
    }

    /*
     * @Description: StdLegalEnterpriseExecuted R228
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Wfm
     * @Date: 2018/12/18 13:26
     */

    public static void etlCleanStdLegalEnterpriseExecutedR228(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONArray r228 = stringJsonObject.getJSONArray("R228");
        if (r228.size() > 0) {
            for (Object r228Obj : r228) {
                JSONObject r228Object = (JSONObject) r228Obj;
                JSONArray r228Data = r228Object.getJSONArray("data");
                if (r228Data.size() > 0) {
                    List<StdLegalEnterpriseExecuted> stdLegalEnterpriseExecutedList = new ArrayList<>();
                    StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted = null;
                    for (Object alterObj : r228Data) {
                        stdLegalEnterpriseExecuted = new StdLegalEnterpriseExecuted();
                        JSONObject alterObject = (JSONObject) alterObj;
                        stdLegalEnterpriseExecuted.setUuID(UUIDGenerator.generate());
                        stdLegalEnterpriseExecuted.setBusinessID(businessID);
                        stdLegalEnterpriseExecuted.setReqID(reqID);
                        stdLegalEnterpriseExecuted.setDatasourcefrom(dataSourceFrom);

                        stdLegalEnterpriseExecuted.setCasecode(alterObject.getString("CASECODE"));
                        stdLegalEnterpriseExecuted.setCasecreatetime(DateUtils.strToDate(alterObject.getString("CASECREATETIME")));
                        stdLegalEnterpriseExecuted.setExeccourtname(alterObject.getString("EXECCOURTNAME"));
                        stdLegalEnterpriseExecuted.setExecmoney(alterObject.getString("EXECMONEY"));
                        stdLegalEnterpriseExecuted.setInputtime(DateUtils.strToDate(alterObject.getString("INPUTTIME")));
                        stdLegalEnterpriseExecuted.setPartycardnum(alterObject.getString("PARTYCARDNUM"));
                        stdLegalEnterpriseExecuted.setPname(alterObject.getString("PNAME"));
                        stdLegalEnterpriseExecuted.setSerialno(alterObject.getString("SERIALNO"));
                        stdLegalEnterpriseExecuted.setUpdatetime(DateUtils.strToDate(alterObject.getString("UPDATETIME")));
                        stdLegalEnterpriseExecutedList.add(stdLegalEnterpriseExecuted);
                    }

                    SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
                    ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
                    try {
                        eTLCleanWriteDao.inserstdLegalEnterpriseExecutedList(stdLegalEnterpriseExecutedList);
                        sqlSession.commit();
                    } catch (Exception e) {
                        sqlSession.rollback();
                        e.printStackTrace();
                        /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                                + ",std_legal_enterprise_executed标准表入库失败！"
                                + System.getProperty("line.separator")
                                , e);*/
                    } finally {
                        stdLegalEnterpriseExecutedList.clear();
                        sqlSession.close();
                    }
                }

            }
        }
    }
    /*
    * @Description: StdLegalIndUnexecuted R231
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/18 13:26
    */
    public static void etlCleanStdLegalIndUnexecutedR231(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONArray r231 = stringJsonObject.getJSONArray("R231");
        if (r231.size() > 0) {
            for (Object r231Obj : r231) {
                JSONObject r231Object = (JSONObject) r231Obj;
                JSONArray r231Data = r231Object.getJSONArray("data");
                if (r231Data.size() > 0) {
                    List<StdLegalIndUnexecuted> stdLegalIndUnexecutedList = new ArrayList<>();
                    StdLegalIndUnexecuted StdLegalIndUnexecuted = null;
                    for (Object alterObj : r231Data) {
                        StdLegalIndUnexecuted = new StdLegalIndUnexecuted();
                        JSONObject alterObject = (JSONObject) alterObj;
                        StdLegalIndUnexecuted.setUuID(UUIDGenerator.generate());
                        StdLegalIndUnexecuted.setBusinessID(businessID);
                        StdLegalIndUnexecuted.setReqID(reqID);
                        StdLegalIndUnexecuted.setDatasourcefrom(dataSourceFrom);

                        StdLegalIndUnexecuted.setID(alterObject.getString("ID"));
                        StdLegalIndUnexecuted.setIname(alterObject.getString("INAME"));
                        StdLegalIndUnexecuted.setAge(alterObject.getString("AGE"));
                        StdLegalIndUnexecuted.setSexy(alterObject.getString("SEXY"));
                        StdLegalIndUnexecuted.setCardnnum(alterObject.getString("CARDNUM"));
                        StdLegalIndUnexecuted.setCasecode(alterObject.getString("CASECODE"));
                        StdLegalIndUnexecuted.setCourtname(alterObject.getString("COURTNAME"));
                        StdLegalIndUnexecuted.setAreaname(alterObject.getString("AREANAME"));
                        StdLegalIndUnexecuted.setGistid(alterObject.getString("GISTID"));
                        StdLegalIndUnexecuted.setRegdate(DateUtils.strToDate(EtlCleanRules.changeDirectionh(alterObject.getString("REGDATE"))));
                        StdLegalIndUnexecuted.setGistunit(alterObject.getString("GISTUNIT"));
                        StdLegalIndUnexecuted.setPerformance(alterObject.getString("PERFORMANCE"));
                        StdLegalIndUnexecuted.setDisrupttypename(alterObject.getString("DISRUPTTYPENAME"));
                        StdLegalIndUnexecuted.setDuty(alterObject.getString("DUTY"));
                        StdLegalIndUnexecuted.setPublishdate(DateUtils.strToDate(EtlCleanRules.stampToDate(alterObject.getString("PUBLISHDATE"))));
                        StdLegalIndUnexecuted.setInputtime(DateUtils.strToDate(EtlCleanRules.changeDirectionh(alterObject.getString("INPUTTIME"))));
                        StdLegalIndUnexecuted.setUpdatetime(DateUtils.strToDate(EtlCleanRules.changeDirectionh(alterObject.getString("UPDATETIME"))));
                        stdLegalIndUnexecutedList.add(StdLegalIndUnexecuted);
                    }

                    SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
                    ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
                    try {
                        eTLCleanWriteDao.inserStdLegalIndUnexecutedList(stdLegalIndUnexecutedList);
                        sqlSession.commit();
                    } catch (Exception e) {
                        sqlSession.rollback();
                        e.printStackTrace();
                        /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                                + ",std_legal_ind_unexecuted标准表入库失败！"
                                + System.getProperty("line.separator")
                                , e);*/
                    } finally {
                        stdLegalIndUnexecutedList.clear();
                        sqlSession.close();
                    }
                }
            }
        }
    }

    /*
    * @Description: StdLegalIndividualExecuted R229
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/19 11:56
    */
    public static void etlCleanStdLegalIndividualExecutedR229(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONArray r229 = stringJsonObject.getJSONArray("R229");
        if (r229.size() > 0) {
            for (Object r229Obj : r229) {
                JSONObject r229Object = (JSONObject) r229Obj;
                JSONArray r229Data = r229Object.getJSONArray("data");
                if (r229Data.size() > 0) {
                    List<StdLegalIndividualExecuted> stdLegalIndividualExecutedList = new ArrayList<>();
                    StdLegalIndividualExecuted stdLegalIndividualExecuted = null;
                    for (Object alterObj : r229Data) {
                        stdLegalIndividualExecuted = new StdLegalIndividualExecuted();
                        JSONObject alterObject = (JSONObject) alterObj;
                        stdLegalIndividualExecuted.setUuID(UUIDGenerator.generate());
                        stdLegalIndividualExecuted.setBusinessID(businessID);
                        stdLegalIndividualExecuted.setReqID(reqID);
                        stdLegalIndividualExecuted.setDatasourcefrom(dataSourceFrom);

                        stdLegalIndividualExecuted.setPname(alterObject.getString("PNAME"));
                        stdLegalIndividualExecuted.setCasecode(alterObject.getString("CASECODE"));
                        stdLegalIndividualExecuted.setCasecreatetime(DateUtils.strToDate(alterObject.getString("CASECREATETIME")));
                        stdLegalIndividualExecuted.setExeccourtname(alterObject.getString("EXECCOURTNAME"));
                        stdLegalIndividualExecuted.setExecmoney(alterObject.getString("EXECMONEY"));
                        stdLegalIndividualExecuted.setInputtime(DateUtils.strToDate(alterObject.getString("INPUTTIME")));
                        stdLegalIndividualExecuted.setPartycardnum(alterObject.getString("PARTYCARDNUM"));
                        stdLegalIndividualExecuted.setSerialno(alterObject.getString("SERIALNO"));
                        stdLegalIndividualExecuted.setUpdatetime(DateUtils.strToDate(alterObject.getString("UPDATETIME")));
                        stdLegalIndividualExecutedList.add(stdLegalIndividualExecuted);
                    }

                    SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
                    ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
                    try {
                        eTLCleanWriteDao.inserStdLegalIndividualExecutedList(stdLegalIndividualExecutedList);
                        sqlSession.commit();
                    } catch (Exception e) {
                        sqlSession.rollback();
                        e.printStackTrace();
                        /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                                + ",std_legal_individual_executed标准表入库失败！"
                                + System.getProperty("line.separator")
                                , e);*/
                    } finally {
                        stdLegalIndividualExecutedList.clear();
                        sqlSession.close();
                    }
                }
            }
        }
    }

    /*
    * @Description: StdLegalEntUnexecuted R230
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/19 13:46
    */
    public static void etlCleanStdLegalEntUnexecutedR230(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONArray r230 = stringJsonObject.getJSONArray("R230");
        if (r230.size() > 0) {
            for (Object r230Obj : r230) {
                JSONObject r230Object = (JSONObject) r230Obj;
                JSONArray r230Data = r230Object.getJSONArray("data");
                if (r230Data.size() > 0) {
                    List<StdLegalEntUnexecuted> stdLegalEntUnexecutedList = new ArrayList<>();
                    StdLegalEntUnexecuted stdLegalEntUnexecuted = null;
                    for (Object alterObj : r230Data) {
                        stdLegalEntUnexecuted = new StdLegalEntUnexecuted();
                        JSONObject alterObject = (JSONObject) alterObj;
                        stdLegalEntUnexecuted.setUuID(UUIDGenerator.generate());
                        stdLegalEntUnexecuted.setBusinessID(businessID);
                        stdLegalEntUnexecuted.setReqID(reqID);
                        stdLegalEntUnexecuted.setDatasourcefrom(dataSourceFrom);

                        stdLegalEntUnexecuted.setAreaname(alterObject.getString("AREANAME"));
                        stdLegalEntUnexecuted.setCardnnum(alterObject.getString("CARDNUM"));
                        stdLegalEntUnexecuted.setCasecode(alterObject.getString("CASECODE"));
                        stdLegalEntUnexecuted.setCourtname(alterObject.getString("COURTNAME"));
                        stdLegalEntUnexecuted.setDisrupttypename(alterObject.getString("DISRUPTTYPENAME"));
                        stdLegalEntUnexecuted.setDuty(alterObject.getString("DUTY"));
                        stdLegalEntUnexecuted.setGistID(alterObject.getString("GISTID"));
                        stdLegalEntUnexecuted.setGistunit(alterObject.getString("GISTUNIT"));
                        stdLegalEntUnexecuted.setID(alterObject.getString("ID"));
                        stdLegalEntUnexecuted.setIname(alterObject.getString("INAME"));
                        stdLegalEntUnexecuted.setInputtime(DateUtils.strToDate(alterObject.getString("INPUTTIME")));
                        stdLegalEntUnexecuted.setPerformance(alterObject.getString("PERFORMANCE"));
                        stdLegalEntUnexecuted.setPublishdate(DateUtils.strToDate(alterObject.getString("PUBLISHDATE")));
                        stdLegalEntUnexecuted.setRegdate(DateUtils.strToDate(alterObject.getString("REGDATE")));
                        stdLegalEntUnexecuted.setUpdatetime(DateUtils.strToDate(alterObject.getString("UPDATETIME")));
                        stdLegalEntUnexecutedList.add(stdLegalEntUnexecuted);
                    }

                    SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
                    ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
                    try {
                        eTLCleanWriteDao.inserstdLegalEntUnexecutedList(stdLegalEntUnexecutedList);
                        sqlSession.commit();
                    } catch (Exception e) {
                        sqlSession.rollback();
                        e.printStackTrace();
                        /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                                + ",std_legal_ent_unexecuted标准表入库失败！"
                                + System.getProperty("line.separator")
                                , e);*/
                    } finally {
                        stdLegalEntUnexecutedList.clear();
                        sqlSession.close();
                    }
                }
            }
        }
    }


    /*
    * @Description: StdLegalDataStructured R227
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/19 15:57
    */
    public static void etlCleanStdLegalDataStructuredR227(String amarsoftData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(amarsoftData);
        JSONArray r227 = stringJsonObject.getJSONArray("R227");
        if (r227.size() > 0) {
            for (Object r227Obj : r227) {
                JSONObject r227Object = (JSONObject) r227Obj;
                JSONArray r227Data = r227Object.getJSONArray("data");
                if (r227Data.size() > 0) {
                    List<StdLegalDataStructured> stdLegalDataStructuredList = new ArrayList<>();
                    StdLegalDataStructured stdLegalDataStructured = null;
                    for (Object alterObj : r227Data) {
                        stdLegalDataStructured = new StdLegalDataStructured();
                        JSONObject alterObject = (JSONObject) alterObj;
                        stdLegalDataStructured.setUuID(UUIDGenerator.generate());
                        stdLegalDataStructured.setBusinessID(businessID);
                        stdLegalDataStructured.setReqID(reqID);
                        stdLegalDataStructured.setDatasourcefrom(dataSourceFrom);

                        stdLegalDataStructured.setAgent(alterObject.getString("AGENT"));
                        stdLegalDataStructured.setBillnumer(alterObject.getString("BILLNUMER"));
                        stdLegalDataStructured.setCasedate(DateUtils.strToDate(EtlCleanRules.changeDirectionh(alterObject.getString("CASEDATE"))));                //19
                        stdLegalDataStructured.setCaseno(alterObject.getString("CASENO"));
                        stdLegalDataStructured.setCasereason(alterObject.getString("CASEREASON"));
                        stdLegalDataStructured.setChiefjudge(alterObject.getString("CHIEFJUDGE"));
                        stdLegalDataStructured.setCity(alterObject.getString("CITY"));
                        stdLegalDataStructured.setCollectiondate(DateUtils.strToDate(EtlCleanRules.changeDirectionh(alterObject.getString("COLLECTIONDATE"))));     //19
                        stdLegalDataStructured.setCourt(alterObject.getString("COURT"));
                        stdLegalDataStructured.setDatasource(alterObject.getString("DATASOURCE"));
                        stdLegalDataStructured.setDepartment(alterObject.getString("DEPARTMENT"));
                        stdLegalDataStructured.setDocuclass(alterObject.getString("DOCUCLASS"));
                        stdLegalDataStructured.setEndorser(alterObject.getString("ENDORSER"));
                        stdLegalDataStructured.setEntname(alterObject.getString("ENTNAME"));
                        stdLegalDataStructured.setExpirationdate(DateUtils.strToDate(EtlCleanRules.changeDirectionh(alterObject.getString("EXPIRATIONDATE"))));       //19
                        stdLegalDataStructured.setHolder(alterObject.getString("HOLDER"));
                        stdLegalDataStructured.setImportstaff(alterObject.getString("IMPORTSTAFF"));
                        stdLegalDataStructured.setJudge(alterObject.getString("JUDGE"));
                        stdLegalDataStructured.setJudgementresult(alterObject.getString("JUDGEMENTRESULT"));
                        stdLegalDataStructured.setLawstatus(alterObject.getString("LAWSTATUS"));
                        stdLegalDataStructured.setParty(alterObject.getString("PARTY"));
                        stdLegalDataStructured.setPaybank(alterObject.getString("PAYBANK"));
                        stdLegalDataStructured.setPayee(alterObject.getString("PAYEE"));
                        stdLegalDataStructured.setPdate(DateUtils.strToDate(EtlCleanRules.changeDirectionh(alterObject.getString("PDATE"))));                       //19
                        stdLegalDataStructured.setPdesc(alterObject.getString("PDESC"));
                        stdLegalDataStructured.setPlaintiff(alterObject.getString("PLAINTIFF"));
                        stdLegalDataStructured.setProvince(alterObject.getString("PROVINCE"));
                        stdLegalDataStructured.setPtype(alterObject.getString("PTYPE"));
                        stdLegalDataStructured.setSecretary(alterObject.getString("SECRETARY"));
                        stdLegalDataStructured.setSerialno(alterObject.getString("SERIALNO"));
                        stdLegalDataStructured.setTarget(alterObject.getString("TARGET"));
                        stdLegalDataStructured.setTargetamount(EtlCleanRules.deleteZero(EtlCleanRules.deleteSpecialCharacters(alterObject.getString("TARGETAMOUNT"))));   //9,12,16,18,17
                        // System.err.println(EtlCleanRules.DeleteZero(EtlCleanRules.DeleteSpecialCharacters(alterObject.getString("TARGETAMOUNT"))));
                        stdLegalDataStructured.setTargettype(alterObject.getString("TARGETTYPE"));
                        stdLegalDataStructured.setTelno(alterObject.getString("TELNO"));
                        stdLegalDataStructured.setTickettime(DateUtils.strToDate(EtlCleanRules.changeDirectionh(alterObject.getString("TICKETTIME"))));             //19
                        stdLegalDataStructured.setWinstaff(alterObject.getString("WINSTAFF"));
                        stdLegalDataStructuredList.add(stdLegalDataStructured);
                    }

                    SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
                    ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
                    try {
                        eTLCleanWriteDao.inserStdLegalDataStructuredList(stdLegalDataStructuredList);
                        sqlSession.commit();
                    } catch (Exception e) {
                        sqlSession.rollback();
                        e.printStackTrace();
                        /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                                + ",std_legal_data_structured标准表入库失败！"
                                + System.getProperty("line.separator")
                                , e);*/
                    } finally {
                        stdLegalDataStructuredList.clear();
                        sqlSession.close();
                    }
                }
            }
        }
    }

}