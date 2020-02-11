package com.dfwy.online.sparkstreamingtask.vcloude.task.etlclean;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao;
import common.pojo.applicantinformation.ApplicantInformationTO;
import common.pojo.etlstandardtable.*;
import common.utils.date.DateUtils;
import common.utils.etlclean.EtlCleanRules;
import common.utils.sqlsessionfactoryutil.SqlSessionFactoryUtil;
import common.utils.uuid.UUIDGenerator;
import org.apache.ibatis.session.SqlSession;

import java.util.ArrayList;
import java.util.List;


public class CCXETLInsertData {
    public static void etlClean(ApplicantInformationTO applicantInformationTO)  {
        //将传输过来的报文数据封装进申请人实例中，做数据的传输！
        applicantInformationTO.setDataSourceFrom("ccxData");
        etlCleanStdEntAlterList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntFiliationList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntLiquidationList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntSharesfrostList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntBasicList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntCaseInfoList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntInvitemList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntLrinvList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntLrpositionList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntMorGuainfoList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntPersonList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalIndUnexecutedR231(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalEntUnexecutedR230(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalIndividualExecutedR229(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdLegalEnterpriseExecuted(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntShareHolderList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
        etlCleanStdEntSharesImpawnList(applicantInformationTO.getCcxData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),applicantInformationTO.getDataSourceFrom());
    }

    //以下是中诚信接口
    public static void etlCleanStdEntAlterList(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray alterList = dataObject.getJSONArray("ALTER");
        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (alterList.size() > 0) {
            List<StdEntAlterList> stdEntAlterListList = new ArrayList<>();
            StdEntAlterList stdEntAlterList = null;

            for (Object alterObj : alterList) {
                stdEntAlterList = new StdEntAlterList();
                JSONObject alterObject = (JSONObject) alterObj;
                stdEntAlterList.setUuID(UUIDGenerator.generate());
                stdEntAlterList.setBusinessID(businessID);
                stdEntAlterList.setReqID(reqID);
                stdEntAlterList.setDatasourcefrom(dataSourceFrom);

                stdEntAlterList.setAltaf(alterObject.getString("ALTAF"));
                stdEntAlterList.setAltbe(alterObject.getString("ALTBE"));
                stdEntAlterList.setAltdate(DateUtils.strToDate(alterObject.getString("ALTDATE")));
                stdEntAlterList.setAltitem(alterObject.getString("ALTITEM"));
                stdEntAlterListList.add(stdEntAlterList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                eTLCleanWriteDao.inserStdEntAlterListList(stdEntAlterListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
                /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_alter_list标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntAlterListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description:
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 11:25
    */
    public static void etlCleanStdEntFiliationList(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray filiationList = dataObject.getJSONArray("FILIATION");

        List<StdEntAlterList> stdEntAlterListList = new ArrayList<>();
        StdEntAlterList stdEntAlterList = null;

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

                stdEntFiliationList.setBrname(filiationListObject.getString("BRNAME"));
                stdEntFiliationList.setBrregno(filiationListObject.getString("BRREGNO"));
                stdEntFiliationList.setBrprincipal(filiationListObject.getString("BRPRINCIPAL"));
                stdEntFiliationList.setCbuitem(filiationListObject.getString("CBUITEM"));
                stdEntFiliationList.setBraddr(filiationListObject.getString("BRADDR"));
                stdEntFiliationListList.add(stdEntFiliationList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
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
     * @Description:  std_ent_liquidation_list
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Wfm
     * @Date: 2018/12/22 11:38
     */
    public static void etlCleanStdEntLiquidationList(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray liquidationList = dataObject.getJSONArray("LIQUIDATION");

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

                stdEntLiquidationList.setLigentity(liquidationListObject.getString("LIGENTITY"));
                stdEntLiquidationList.setLigprincipal(liquidationListObject.getString("LIGPRINCIPAL"));
                stdEntLiquidationList.setLiqmen(liquidationListObject.getString("LIQMEN"));
                stdEntLiquidationList.setLigst(liquidationListObject.getString("LIGST"));
                stdEntLiquidationList.setLigenddate(DateUtils.strToDate(liquidationListObject.getString("LIGENDDATE")));
                stdEntLiquidationList.setDebttranee(liquidationListObject.getString("DEBTTRANEE"));
                stdEntLiquidationList.setClaimtranee(liquidationListObject.getString("CLAIMTRANEE"));

                stdEntLiquidationListList.add(stdEntLiquidationList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
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
    * @Description:  std_ent_sharesfrost_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 11:45
    */
    public static void etlCleanStdEntSharesfrostList(String ccxData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray sharesFrostList = dataObject.getJSONArray("SHARESFROST");

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

                stdEntSharesfrostList.setFrodocno(sharesFrostListObject.getString("FRODOCNO"));
                stdEntSharesfrostList.setFroauth(sharesFrostListObject.getString("FROAUTH"));
                stdEntSharesfrostList.setFrofrom(DateUtils.strToDate(sharesFrostListObject.getString("FROFROM")));
                stdEntSharesfrostList.setFroto(DateUtils.strToDate(sharesFrostListObject.getString("FROTO")));
                stdEntSharesfrostList.setFroam(sharesFrostListObject.getDouble("FROAM"));
                stdEntSharesfrostList.setThawauth(sharesFrostListObject.getString("THAWAUTH"));
                stdEntSharesfrostList.setThawdocno(sharesFrostListObject.getString("THAWDOCNO"));
                stdEntSharesfrostList.setThawdate(DateUtils.strToDate(sharesFrostListObject.getString("THAWDATE")));
                stdEntSharesfrostList.setThawcomment(sharesFrostListObject.getString("THAWCOMMENT"));

                stdEntSharesfrostListList.add(stdEntSharesfrostList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
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
    * @Description: std_ent_basic_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 11:45
    */
    public static void etlCleanStdEntBasicList(String ccxData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray basicList = dataObject.getJSONArray("BASIC");


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

                stdEntBasicList.setEntname(basicObject.getString("ENTNAME"));
                stdEntBasicList.setCreditcode(basicObject.getString("CREDITCODE"));
                stdEntBasicList.setOrgcode(basicObject.getString("ORGCODES"));
                stdEntBasicList.setRegno(basicObject.getString("REGNO"));
                stdEntBasicList.setEnttype(basicObject.getString("ENTTYPE"));
                stdEntBasicList.setLrname(basicObject.getString("FRNAME"));
                stdEntBasicList.setRegcap(basicObject.getDouble("REGCAP"));
                stdEntBasicList.setRegcapcur(basicObject.getString("REGCAPCUR"));
                stdEntBasicList.setReccap(basicObject.getDouble("RECCAP"));

                stdEntBasicList.setRegorg(basicObject.getString("REGORG"));
                stdEntBasicList.setRegstatus(basicObject.getString("ENTSTATUS"));
                stdEntBasicList.setAddress(basicObject.getString("DOM"));
                stdEntBasicList.setAbuitem(basicObject.getString("ABUITEM"));

                stdEntBasicList.setOperatescope(basicObject.getString("ZSOPSCOPE"));
                stdEntBasicList.setOperatescopeandform(basicObject.getString("ZSOPSCOPE"));


                stdEntBasicList.setOriregno(basicObject.getString("ORIREGNO"));

                stdEntBasicList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("ESDATE"))));
                stdEntBasicList.setOpenfrom(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("OPFROM"))));
                stdEntBasicList.setOpento(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("OPTO"))));
                stdEntBasicList.setApprdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("APPRDATE"))));
                stdEntBasicList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("CANDATE"))));
                stdEntBasicList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basicObject.getString("REVDATE"))));
                stdEntBasicList.setAncheyear(EtlCleanRules.dateFormatCleaning(basicObject.getString("ANCHEYEAR")));
                stdEntBasicListList.add(stdEntBasicList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntBasicListList(stdEntBasicListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
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
    * @Description: std_ent_caseinfo_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 13:35
    */
    public static void etlCleanStdEntCaseInfoList(String ccxData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray caseInfoList = dataObject.getJSONArray("ENTCASEBASEINFO");


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

                stdEntCaseInfoList.setIllegfact(caseInfoListObject.getString("ILLEGACTTYPE"));
                stdEntCaseInfoList.setPenauth(caseInfoListObject.getString("PENAUTH"));
                stdEntCaseInfoList.setPenresult(caseInfoListObject.getString("PENCONTENT"));
                stdEntCaseInfoList.setPendecno(caseInfoListObject.getString("PENDECNO"));
                stdEntCaseInfoList.setPentype(caseInfoListObject.getString("PENTYPE"));
                stdEntCaseInfoList.setPendecissdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(caseInfoListObject.getString("PENDECISSDATE"))));


                stdEntCaseInfoListList.add(stdEntCaseInfoList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntCaseInfoListList(stdEntCaseInfoListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
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

    /*
    * @Description:   std_ent_invitem_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 13:51
    */
    public static void etlCleanStdEntInvitemList(String ccxData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray entinvItemList = dataObject.getJSONArray("ENTINV");


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

                stdEntInvitemList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvItemListObject.getString("CANDATE"))));
                stdEntInvitemList.setCurrency(entinvItemListObject.getString("CONGROCUR"));
                stdEntInvitemList.setInventname(entinvItemListObject.getString("ENTJGNAME"));
                stdEntInvitemList.setRegstatus(entinvItemListObject.getString("ENTSTATUS"));
                stdEntInvitemList.setInventtype(entinvItemListObject.getString("ENTTYPE"));
                stdEntInvitemList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvItemListObject.getString("ESDATE"))));

                stdEntInvitemList.setFundedratio(
                        String.valueOf(
                                EtlCleanRules.deletePercentSign(
                                        entinvItemListObject.getString("FUNDEDRATIO"))));

                stdEntInvitemList.setInvlrname(entinvItemListObject.getString("NAME"));


                String regCap = EtlCleanRules.deleteZero(
                        EtlCleanRules.deleteSpecialCharacters(
                                EtlCleanRules.deletePlus(
                                        EtlCleanRules.amountDeleteComma(
                                                entinvItemListObject.getString("REGCAP")))));
                stdEntInvitemList.setInvregcap(regCap);

                stdEntInvitemList.setRegcapcur(entinvItemListObject.getString("REGCAPCUR"));
                stdEntInvitemList.setInvregno(entinvItemListObject.getString("REGNO"));
                stdEntInvitemList.setRegorg(entinvItemListObject.getString("REGORG"));

                stdEntInvitemList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvItemListObject.getString("REVDATE"))));

                String subConam = EtlCleanRules.amountDeleteComma(entinvItemListObject.getString("SUBCONAM"));
                String s0 = EtlCleanRules.deletePlus(subConam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntInvitemList.setSubconam(Double.parseDouble(s2));


                stdEntInvitemListList.add(stdEntInvitemList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
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
    * @Description: std_ent_lrinv_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/22 14:24
    */
    public static void etlCleanStdEntLrinvList(String ccxData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray frinvList = dataObject.getJSONArray("FRINV");


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

                stdEntLrinvList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvObject.getString("CANDATE"))));
                stdEntLrinvList.setCurrency(frinvObject.getString("CURRENCY"));
                stdEntLrinvList.setInventname(frinvObject.getString("ENTNAME"));
                stdEntLrinvList.setRegstatus(frinvObject.getString("ENTSTATUS"));
                stdEntLrinvList.setInventtype(frinvObject.getString("ENTTYPE"));
                stdEntLrinvList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvObject.getString("ESDATE"))));
                stdEntLrinvList.setFundedratio(
                        String.valueOf(
                                EtlCleanRules.deletePercentSign(
                                        frinvObject.getString("FUNDEDRATIO"))));
                stdEntLrinvList.setLrname(frinvObject.getString("NAME"));

                String regCap = EtlCleanRules.deleteZero(
                        EtlCleanRules.deleteSpecialCharacters(
                                EtlCleanRules.deletePlus(
                                        EtlCleanRules.amountDeleteComma(
                                                frinvObject.getString("REGCAP")))));
                stdEntLrinvList.setInvregcap(regCap);

                stdEntLrinvList.setRegcapcur(frinvObject.getString("REGCAPCUR"));
                stdEntLrinvList.setInvregno(frinvObject.getString("REGNO"));
                stdEntLrinvList.setRegorg(frinvObject.getString("REGORG"));
                stdEntLrinvList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvObject.getString("REVDATE"))));

                String subConam = EtlCleanRules.amountDeleteComma(frinvObject.getString("SUBCONAM"));
                String s0 = EtlCleanRules.deletePlus(subConam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntLrinvList.setSubconam(Double.parseDouble(s2));


                stdEntLrinvListList.add(stdEntLrinvList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
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
    * @Description: std_ent_lrposition_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Dxx
    * @Date: 2018/12/22 9:54
    */
    public static void etlCleanStdEntLrpositionList(String ccxData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray frPositionList = dataObject.getJSONArray("FRPOSITION");


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

                stdEntLrpositionList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frPositionObject.getString("CANDATE"))));
                stdEntLrpositionList.setPostionentname(frPositionObject.getString("ENTNAME"));
                stdEntLrpositionList.setRegstatus(frPositionObject.getString("ENTSTATUS"));
                stdEntLrpositionList.setPostionenttype(frPositionObject.getString("ENTTYPE"));
                stdEntLrpositionList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frPositionObject.getString("ESDATE"))));
                stdEntLrpositionList.setLerepsign(frPositionObject.getString("LEREPSIGN"));
                stdEntLrpositionList.setLrname(frPositionObject.getString("NAME"));
                stdEntLrpositionList.setPosition(frPositionObject.getString("POSITION"));
                stdEntLrpositionList.setPostionregcap(frPositionObject.getDouble("REGCAP"));
                stdEntLrpositionList.setRegcapcur(frPositionObject.getString("REGCAPCUR"));
                stdEntLrpositionList.setPostionregno(frPositionObject.getString("REGNO"));
                stdEntLrpositionList.setRegorg(frPositionObject.getString("REGORG"));
                stdEntLrpositionList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frPositionObject.getString("REVDATE"))));


                stdEntLrpositionListList.add(stdEntLrpositionList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntLrpositionListList(stdEntLrpositionListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
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
    * @Description: std_ent_mor_guainfo_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 15:00
    */
    public static void etlCleanStdEntMorGuainfoList(String ccxData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray morguaInfoList = dataObject.getJSONArray("MORTGAGEPAWN");

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


                String quan = EtlCleanRules.deleteAllSpace(morguaInfoListObject.getString("MAB_PAWN_DETAILS"));
                String s = EtlCleanRules.deleteMinus(quan);
                String s1 = EtlCleanRules.deletePlus(s);
                String s2 = EtlCleanRules.deleteSpecialCharacters(s1);
                String s3 = EtlCleanRules.deleteZero(s2);
                String numeric = EtlCleanRules.isNumeric(s3);
                stdEntMorGuainfoList.setQuan(numeric);
                stdEntMorGuainfoList.setGuaname(morguaInfoListObject.getString("MAB_PAWN_NAME"));

                stdEntMorGuainfoListList.add(stdEntMorGuainfoList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
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
    * @Description:   std_ent_person_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 15:10
    */
    public static void etlCleanStdEntPersonList(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray personList = dataObject.getJSONArray("PERSON");


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

                stdEntPersonList.setName(personObject.getString("PERNAME"));
                stdEntPersonList.setPosition(personObject.getString("POSITION"));
                stdEntPersonList.setSex(personObject.getString("SEX"));


                stdEntPersonListList.add(stdEntPersonList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
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
    * @Description: std_legal_ind_unexecuted R231
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/18 15:26
    */
    public static void etlCleanStdLegalIndUnexecutedR231(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray IndUnexecutedList = dataObject.getJSONArray("PUNISHBREAK");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (IndUnexecutedList.size() > 0) {
            List<StdLegalIndUnexecuted> stdLegalIndUnexecutedList = new ArrayList<>();
            StdLegalIndUnexecuted StdLegalIndUnexecuted = null;
            // List<StdEntAlterList> StdEntAlterListList = new ArrayList<>();
            // StdEntAlterList stdEntAlterList = null;
            for (Object alterObj : IndUnexecutedList) {
                StdLegalIndUnexecuted = new StdLegalIndUnexecuted();
                JSONObject alterObject = (JSONObject) alterObj;
                StdLegalIndUnexecuted.setUuID(UUIDGenerator.generate());
                StdLegalIndUnexecuted.setBusinessID(businessID);
                StdLegalIndUnexecuted.setReqID(reqID);
                StdLegalIndUnexecuted.setDatasourcefrom(dataSourceFrom);


                StdLegalIndUnexecuted.setAge(alterObject.getString("AGECLEAN"));
                StdLegalIndUnexecuted.setAreaname(alterObject.getString("AREANAMECLEAN"));
                StdLegalIndUnexecuted.setCardnnum(alterObject.getString("CARDNUM"));
                StdLegalIndUnexecuted.setCasecode(alterObject.getString("CASECODE"));
                StdLegalIndUnexecuted.setCourtname(alterObject.getString("COURTNAME"));
                StdLegalIndUnexecuted.setDisrupttypename(alterObject.getString("DISRUPTTYPENAME"));
                StdLegalIndUnexecuted.setDuty(alterObject.getString("DUTY"));
                StdLegalIndUnexecuted.setGistid(alterObject.getString("GISTID"));
                StdLegalIndUnexecuted.setGistunit(alterObject.getString("GISTUNIT"));
                StdLegalIndUnexecuted.setIname(alterObject.getString("INAMECLEAN"));
                StdLegalIndUnexecuted.setPerformance(alterObject.getString("PERFORMANCE"));
                StdLegalIndUnexecuted.setPublishdate(DateUtils.strToDate(alterObject.getString("PUBLISHDATECLEAN")));
                StdLegalIndUnexecuted.setRegdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(alterObject.getString("REGDATECLEAN"))));//
                StdLegalIndUnexecuted.setSexy(alterObject.getString("SEXYCLEAN"));


                stdLegalIndUnexecutedList.add(StdLegalIndUnexecuted);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                eTLCleanWriteDao.inserStdLegalIndUnexecutedList(stdLegalIndUnexecutedList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            } finally {
                stdLegalIndUnexecutedList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description: std_legal_ent_unexecuted R230
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 16:51
    */
    public static void etlCleanStdLegalEntUnexecutedR230(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray EntUnexecutedList = dataObject.getJSONArray("PUNISHBREAK");
        if (EntUnexecutedList.size() > 0) {
            List<StdLegalEntUnexecuted> stdLegalEntUnexecutedList = new ArrayList<>();
            StdLegalEntUnexecuted stdLegalEntUnexecuted = null;

            for (Object alterObj : EntUnexecutedList) {
                stdLegalEntUnexecuted = new StdLegalEntUnexecuted();
                JSONObject alterObject = (JSONObject) alterObj;
                stdLegalEntUnexecuted.setUuID(UUIDGenerator.generate());
                stdLegalEntUnexecuted.setBusinessID(businessID);
                stdLegalEntUnexecuted.setReqID(reqID);
                stdLegalEntUnexecuted.setDatasourcefrom(dataSourceFrom);

                stdLegalEntUnexecuted.setAreaname(alterObject.getString("AREANAMECLEAN"));
                stdLegalEntUnexecuted.setCardnnum(alterObject.getString("CARDNUM"));
                stdLegalEntUnexecuted.setCasecode(alterObject.getString("CASECODE"));
                stdLegalEntUnexecuted.setCourtname(alterObject.getString("COURTNAME"));
                stdLegalEntUnexecuted.setDisrupttypename(alterObject.getString("DISRUPTTYPENAME"));
                stdLegalEntUnexecuted.setDuty(alterObject.getString("DUTY"));
                stdLegalEntUnexecuted.setGistID(alterObject.getString("GISTID"));
                stdLegalEntUnexecuted.setGistunit(alterObject.getString("GISTUNIT"));
                stdLegalEntUnexecuted.setPerformance(alterObject.getString("PERFORMANCE"));
                stdLegalEntUnexecuted.setPublishdate(DateUtils.strToDate(alterObject.getString("PUBLISHDATECLEAN")));
                stdLegalEntUnexecuted.setRegdate(DateUtils.strToDate(alterObject.getString("REGDATECLEAN")));


                stdLegalEntUnexecutedList.add(stdLegalEntUnexecuted);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                eTLCleanWriteDao.inserstdLegalEntUnexecutedList(stdLegalEntUnexecutedList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            } finally {
                stdLegalEntUnexecutedList.clear();
                sqlSession.close();
            }
        }

    }

    /*
    * @Description: std_legal_individual_executed R229
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 16:55
    */
    public static void etlCleanStdLegalIndividualExecutedR229(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray individualExecutedList = dataObject.getJSONArray("PUNISHED");

        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (individualExecutedList.size() > 0) {
            List<StdLegalIndividualExecuted> stdLegalIndividualExecutedList = new ArrayList<>();
            StdLegalIndividualExecuted stdLegalIndividualExecuted = null;
            for (Object alterObj : individualExecutedList) {
                stdLegalIndividualExecuted = new StdLegalIndividualExecuted();
                JSONObject alterObject = (JSONObject) alterObj;
                stdLegalIndividualExecuted.setUuID(UUIDGenerator.generate());
                stdLegalIndividualExecuted.setBusinessID(businessID);
                stdLegalIndividualExecuted.setReqID(reqID);
                stdLegalIndividualExecuted.setDatasourcefrom(dataSourceFrom);

                stdLegalIndividualExecuted.setPartycardnum(alterObject.getString("CARDNUMCLEAN"));
                stdLegalIndividualExecuted.setCasecode(alterObject.getString("CASECODE"));
                stdLegalIndividualExecuted.setExeccourtname(alterObject.getString("COURTNAME"));
                stdLegalIndividualExecuted.setExecmoney(alterObject.getString("EXECMONEY"));
                stdLegalIndividualExecuted.setPname(alterObject.getString("INAMECLEAN"));
                stdLegalIndividualExecuted.setCasecreatetime(DateUtils.strToDate(alterObject.getString("REGDATECLEAN")));


                stdLegalIndividualExecutedList.add(stdLegalIndividualExecuted);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                eTLCleanWriteDao.inserStdLegalIndividualExecutedList(stdLegalIndividualExecutedList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            } finally {
                stdLegalIndividualExecutedList.clear();
                sqlSession.close();
            }
        }
    }

    /*
     * @Description: std_legal_enterprise_executed R228
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Wfm
     * @Date: 2018/12/22 17:15
     */
    public static void etlCleanStdLegalEnterpriseExecuted(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray EnterpriseExecutedList = dataObject.getJSONArray("PUNISHED");
        //做数据判断，如果获取的JSONArray数组是空值，则停止执行入库程序
        if (EnterpriseExecutedList.size() > 0) {

            List<StdLegalEnterpriseExecuted> stdLegalEnterpriseExecutedList = new ArrayList<>();
            StdLegalEnterpriseExecuted stdLegalEnterpriseExecuted = null;
            for (Object alterObj : EnterpriseExecutedList) {
                stdLegalEnterpriseExecuted = new StdLegalEnterpriseExecuted();
                JSONObject alterObject = (JSONObject) alterObj;
                stdLegalEnterpriseExecuted.setUuID(UUIDGenerator.generate());
                stdLegalEnterpriseExecuted.setBusinessID(businessID);
                stdLegalEnterpriseExecuted.setReqID(reqID);
                stdLegalEnterpriseExecuted.setDatasourcefrom(dataSourceFrom);


                stdLegalEnterpriseExecuted.setPartycardnum(alterObject.getString("CARDNUMCLEAN"));
                stdLegalEnterpriseExecuted.setCasecode(alterObject.getString("CASECODE"));
                stdLegalEnterpriseExecuted.setExeccourtname(alterObject.getString("COURTNAME"));
                stdLegalEnterpriseExecuted.setExecmoney(alterObject.getString("EXECMONEY"));
                stdLegalEnterpriseExecuted.setPname(alterObject.getString("INAMECLEAN"));
                stdLegalEnterpriseExecuted.setCasecreatetime(DateUtils.strToDate(alterObject.getString("REGDATECLEAN")));

                stdLegalEnterpriseExecutedList.add(stdLegalEnterpriseExecuted);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao eTLCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                eTLCleanWriteDao.inserstdLegalEnterpriseExecutedList(stdLegalEnterpriseExecutedList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            } finally {
                stdLegalEnterpriseExecutedList.clear();
                sqlSession.close();
            }
        }


    }


    /*
    * @Description: std_ent_share_holder_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 17:28
    */
    public static void etlCleanStdEntShareHolderList(String ccxData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray shareHolderList = dataObject.getJSONArray("SHAREHOLDER");


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

                stdEntShareHolderList.setCondate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(shareHolderObject.getString("CONDATE"))));
                stdEntShareHolderList.setCountry(shareHolderObject.getString("COUNTRY"));
                stdEntShareHolderList.setFundedratio(String.valueOf(EtlCleanRules.deletePercentSign(shareHolderObject.getString("FUNDEDRATIO"))));
                stdEntShareHolderList.setRegcapcur(shareHolderObject.getString("REGCAPCUR"));
                stdEntShareHolderList.setShareholdername(shareHolderObject.getString("SHANAME"));

                String subConam = EtlCleanRules.amountDeleteComma(shareHolderObject.getString("SUBCONAM"));
                String s0 = EtlCleanRules.deletePlus(subConam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntShareHolderList.setSubconam(Double.parseDouble(s2));

                stdEntShareHolderListList.add(stdEntShareHolderList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
            try {
                ETLCleanWriteDao.inserStdEntShareHolderListList(stdEntShareHolderListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_share_holder_list标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntShareHolderListList.clear();
                sqlSession.close();
            }
        }
    }

    /*
    * @Description: std_ent_shares_impawn_list
    * @param: [amarsoftData, businessID, reqID]
    * @return: void
    * @author:Wfm
    * @Date: 2018/12/22 17:40
    */
    public static void etlCleanStdEntSharesImpawnList(String ccxData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(ccxData);
        JSONObject dataObject = stringJsonObject.getJSONObject("data");
        JSONArray sharesImpawnList = dataObject.getJSONArray("STOCKPAWN");

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
                stdEntSharesImpawnList.setDatasourcefrom(dataSourceFrom);

                stdEntSharesImpawnList.setImpam(sharesImpawnListObject.getDouble("STK_PAWN_CZAMT"));
                stdEntSharesImpawnList.setImpsandate(DateUtils.strToDate(sharesImpawnListObject.getString("STK_PAWN_REGDATE")));
                stdEntSharesImpawnList.setImporg(sharesImpawnListObject.getString("STK_PAWN_ZQPER"));
                stdEntSharesImpawnListList.add(stdEntSharesImpawnList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ETLCleanWriteDao = sqlSession.getMapper(com.dfwy.online.sparkstreamingtask.dao.ETLCleanWriteDao.class);
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

}
