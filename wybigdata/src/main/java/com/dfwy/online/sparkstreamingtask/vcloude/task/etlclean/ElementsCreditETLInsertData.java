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

import java.text.ParseException;
import java.util.ArrayList;;
import java.util.List;

/**
 * @Description:对获取的元素征信报文数据进行数据清洗工作，入mysql标准表
 * @author:Yxx
 * @Date: 2018/12/19 14:10
 * @copyright: 东方微银科技（北京）有限公司
 */

public class ElementsCreditETLInsertData {

    public static void etlClean(ApplicantInformationTO applicantInformationTO) {
        applicantInformationTO.setDataSourceFrom("elementscreditData");
        //1-13 元素征信 工商数据解析
        //1.std_ent_alter_list  企业历史变更信息 --时间格式 00:00:00
        etlCleanStdEntAlterList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //2.std_ent_basic_list  企业照面信息
        etlCleanStdEntBasicList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //3.std_ent_caseinfo_list 行政处罚 ok
        etlCleanStdEntCaseInfoList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //4.std_ent_filiation_list 分支机构 ok
        etlCleanStdEntFiliationList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //5.std_ent_invitem_list 企业对外投资信息 ok  认缴出资额
        etlCleanStdEntInvitemList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //6.std_ent_liquidation_list 清算信息  没有数据要造数据 --ok
        etlCleanStdEntLiquidationList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //7.std_ent_lrinv_list 法定代表人对外投资信息
        etlCleanStdEntLrinvList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //8.std_ent_lrposition_list 法定代表人在其他企业任职信息
        etlCleanStdEntLrpositionList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //9.std_ent_mor_guainfo_list 动产抵押物信息
        etlCleanStdEntMorGuainfoList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //10.std_ent_person_list 企业主要管理人员
        etlCleanStdEntPersonList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //11.std_ent_share_holder_list 企业股东及出资信息
        etlCleanStdEntShareHolderList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //12.std_ent_shares_impawn_list 股权出质历史信息
        etlCleanStdEntSharesImpawnList(applicantInformationTO.getElementscreditData(),
                applicantInformationTO.getBusinessID(),
                applicantInformationTO.getReqID(),
                applicantInformationTO.getDataSourceFrom());
        //13.std_ent_sharesfrost_list 股权冻结历史信息  //
       etlCleanStdEntSharesfrostList(applicantInformationTO.getElementscreditData(),
               applicantInformationTO.getBusinessID(),
               applicantInformationTO.getReqID(),
               applicantInformationTO.getDataSourceFrom());

    }

    /**
     * @Description: std_ent_sharesfrost_list 股权冻结历史信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/20 11:55
     */
    private static void etlCleanStdEntSharesfrostList(String elementscreditData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray sharesfrosts = data.getJSONArray("sharesfrosts");

        if (sharesfrosts.size() > 0) {
            List<StdEntSharesfrostList> stdEntSharesfrostListList = new ArrayList<>();
            StdEntSharesfrostList stdEntSharesfrostList = null;
            for (Object sharesfrostsObj : sharesfrosts) {
                stdEntSharesfrostList = new StdEntSharesfrostList();
                JSONObject sharesfrostsObject = (JSONObject) sharesfrostsObj;
                stdEntSharesfrostList.setUuID(UUIDGenerator.generate());
                stdEntSharesfrostList.setBusinessID(businessID);
                stdEntSharesfrostList.setReqID(reqID);
                stdEntSharesfrostList.setDatasourcefrom(dataSourceFrom);
                stdEntSharesfrostList.setFrodocno(sharesfrostsObject.getString("frodocno"));
                stdEntSharesfrostList.setFroauth(sharesfrostsObject.getString("froauth"));
                stdEntSharesfrostList.setFrofrom(DateUtils.strToDate(sharesfrostsObject.getString("frofrom")));
                stdEntSharesfrostList.setFroto(DateUtils.strToDate(sharesfrostsObject.getString("froto")));
                stdEntSharesfrostList.setFroam(sharesfrostsObject.getDouble("froam"));
                stdEntSharesfrostList.setThawauth(sharesfrostsObject.getString("thawauth"));
                stdEntSharesfrostList.setThawdocno(sharesfrostsObject.getString("thawdocno"));
                stdEntSharesfrostList.setThawdate(DateUtils.strToDate(sharesfrostsObject.getString("thawdate")));
                stdEntSharesfrostList.setThawcomment(sharesfrostsObject.getString("thawcomment"));
                stdEntSharesfrostListList.add(stdEntSharesfrostList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ysetlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ysetlCleanWriteDao.inserStdEntSharesfrostListList(stdEntSharesfrostListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_sharesfrost_list（股权冻结历史信息）标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntSharesfrostListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_shares_impawn_list 股权出质历史信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/20 11:55
     */
    private static void etlCleanStdEntSharesImpawnList(String elementscreditData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray stockpawns = data.getJSONArray("stockpawns");

        if (stockpawns.size() > 0) {
            List<StdEntSharesImpawnList> stdEntSharesImpawnListList = new ArrayList<>();
            StdEntSharesImpawnList stdEntSharesImpawnList = null;
            for (Object stockpawnsObj : stockpawns) {
                stdEntSharesImpawnList = new StdEntSharesImpawnList();
                JSONObject stockpawnsObject = (JSONObject) stockpawnsObj;
                stdEntSharesImpawnList.setUuID(UUIDGenerator.generate());
                stdEntSharesImpawnList.setBusinessID(businessID);
                stdEntSharesImpawnList.setReqID(reqID);
                stdEntSharesImpawnList.setDatasourcefrom(dataSourceFrom);
                stdEntSharesImpawnList.setImporg(stockpawnsObject.getString("stkpawnzqper"));
                stdEntSharesImpawnListList.add(stdEntSharesImpawnList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ysetlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ysetlCleanWriteDao.inserStdEntSharesImpawnListList(stdEntSharesImpawnListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_shares_impawn_list (股权出质历史信息) 标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntSharesImpawnListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_share_holder_list 企业股东及出资信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/20 11:55
     */
    private static void etlCleanStdEntShareHolderList(String elementscreditData, String businessID, String reqID,String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray shareholders = data.getJSONArray("shareholders");

        if (shareholders.size() > 0) {
            List<StdEntShareHolderList> stdEntShareHolderListList = new ArrayList<>();
            StdEntShareHolderList stdEntPersonList = null;
            for (Object shareholdersObj : shareholders) {
                stdEntPersonList = new StdEntShareHolderList();
                JSONObject shareholdersObject = (JSONObject) shareholdersObj;
                stdEntPersonList.setUuID(UUIDGenerator.generate());
                stdEntPersonList.setBusinessID(businessID);
                stdEntPersonList.setReqID(reqID);
                stdEntPersonList.setDatasourcefrom(dataSourceFrom);
                stdEntPersonList.setShareholdername(shareholdersObject.getString("shaname"));

                String subconam = EtlCleanRules.amountDeleteComma(shareholdersObject.getString("subconam"));//9,16,18,17 注册资本
                String s0 = EtlCleanRules.deletePlus(subconam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntPersonList.setSubconam(Double.parseDouble(s2));

                stdEntPersonList.setCondate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(shareholdersObject.getString("condate"))));//19
                stdEntPersonList.setFundedratio(String.valueOf(EtlCleanRules.deletePercentSign(shareholdersObject.getString("fundedratio"))));//11
                stdEntPersonList.setCountry(shareholdersObject.getString("country"));
                stdEntShareHolderListList.add(stdEntPersonList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ysetlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ysetlCleanWriteDao.inserStdEntShareHolderListList(stdEntShareHolderListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_share_holder_list (企业股东及出资信息) 标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntShareHolderListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_person_list 企业主要管理人员
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/20 11:30
     */
    private static void etlCleanStdEntPersonList(String elementscreditData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray persons = data.getJSONArray("persons");

        if (persons.size() > 0) {
            List<StdEntPersonList> stdEntPersonListList = new ArrayList<>();
            StdEntPersonList stdEntPersonList = null;
            for (Object personsObj : persons) {
                stdEntPersonList = new StdEntPersonList();
                JSONObject personsObject = (JSONObject) personsObj;
                stdEntPersonList.setUuID(UUIDGenerator.generate());
                stdEntPersonList.setBusinessID(businessID);
                stdEntPersonList.setReqID(reqID);
                stdEntPersonList.setDatasourcefrom(dataSourceFrom);
                stdEntPersonList.setName(personsObject.getString("pername"));
                stdEntPersonList.setPosition(personsObject.getString("position"));
                stdEntPersonListList.add(stdEntPersonList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ysetlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ysetlCleanWriteDao.inserStdEntPersonListList(stdEntPersonListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_person_list (企业主要管理人员) 标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntPersonListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_mor_guainfo_list 动产抵押物信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/20 11:09
     */
    private static void etlCleanStdEntMorGuainfoList(String elementscreditData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray mortgagepawns = data.getJSONArray("mortgagepawns");

        if (mortgagepawns.size() > 0) {
            List<StdEntMorGuainfoList> stdEntMorGuainfoListList = new ArrayList<>();
            StdEntMorGuainfoList entMorGuainfoList = null;
            for (Object mortgagepawnsObj : mortgagepawns) {
                entMorGuainfoList = new StdEntMorGuainfoList();
                JSONObject mortgagepawnsObject = (JSONObject) mortgagepawnsObj;
                entMorGuainfoList.setUuID(UUIDGenerator.generate());
                entMorGuainfoList.setBusinessID(businessID);
                entMorGuainfoList.setReqID(reqID);
                entMorGuainfoList.setDatasourcefrom(dataSourceFrom);
                entMorGuainfoList.setMorregID(mortgagepawnsObject.getString("mabregno"));
                entMorGuainfoList.setGuaname(mortgagepawnsObject.getString("mabpawnname"));
                //结果为null
                entMorGuainfoList.setQuan(EtlCleanRules.isNumeric(EtlCleanRules.deleteZero(EtlCleanRules.isNumeric(EtlCleanRules.deleteSpecialCharacters(EtlCleanRules.deletePlus(EtlCleanRules.deleteMinus(EtlCleanRules.deleteAllSpace(mortgagepawnsObject.getString("mabpawndetails")))))))));//12,15,16,18,17,7
                stdEntMorGuainfoListList.add(entMorGuainfoList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ysetlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ysetlCleanWriteDao.inserStdEntMorGuainfoListList(stdEntMorGuainfoListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_mor_guainfo_list（动产抵押物信息）标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntMorGuainfoListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_lrposition_list 法定代表人在其他企业任职信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/20 10:30
     */
    private static void etlCleanStdEntLrpositionList(String elementscreditData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray frpositions = data.getJSONArray("frpositions");

        if (frpositions.size() > 0) {
            List<StdEntLrpositionList> stdEntLrpositionListList = new ArrayList<>();
            StdEntLrpositionList stdEntLrpositionList = null;
            for (Object frpositionsObj : frpositions) {
                stdEntLrpositionList = new StdEntLrpositionList();
                JSONObject frpositionsObject = (JSONObject) frpositionsObj;
                stdEntLrpositionList.setUuID(UUIDGenerator.generate());
                stdEntLrpositionList.setBusinessID(businessID);
                stdEntLrpositionList.setReqID(reqID);
                stdEntLrpositionList.setLrname(frpositionsObject.getString("name"));
                stdEntLrpositionList.setPostionentname(frpositionsObject.getString("entname"));
                stdEntLrpositionList.setPostionregno(frpositionsObject.getString("regno"));
                stdEntLrpositionList.setPostionenttype(frpositionsObject.getString("enttype"));
                stdEntLrpositionList.setPostionregcap(frpositionsObject.getDouble("regcap"));
                stdEntLrpositionList.setRegcapcur(frpositionsObject.getString("regcapcur"));
                stdEntLrpositionList.setRegstatus(frpositionsObject.getString("entstatus"));
                stdEntLrpositionList.setDatasourcefrom(dataSourceFrom);
                stdEntLrpositionList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frpositionsObject.getString("candate"))));//19
                stdEntLrpositionList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frpositionsObject.getString("revdate"))));//19
                stdEntLrpositionList.setRegorg(frpositionsObject.getString("regorg"));
                stdEntLrpositionList.setPosition(frpositionsObject.getString("position"));
                stdEntLrpositionList.setLerepsign(frpositionsObject.getString("lerepsign"));
                stdEntLrpositionList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frpositionsObject.getString("esDate"))));//19
                stdEntLrpositionListList.add(stdEntLrpositionList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ysetlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ysetlCleanWriteDao.inserStdEntLrpositionListList(stdEntLrpositionListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_lrposition_list(法定代表人在其他企业任职信息)标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntLrpositionListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_lrinv_list 法定代表人对外投资信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/19 20:44
     */
    private static void etlCleanStdEntLrinvList(String elementscreditData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray frinvs = data.getJSONArray("frinvs");

        if (frinvs.size() > 0) {
            List<StdEntLrinvList> stdEntLrinvListList = new ArrayList<>();
            StdEntLrinvList stdEntLrinvList = null;
            for (Object frinvsObj : frinvs) {
                stdEntLrinvList = new StdEntLrinvList();
                JSONObject frinvsObject = (JSONObject) frinvsObj;
                stdEntLrinvList.setUuID(UUIDGenerator.generate());
                stdEntLrinvList.setReqID(reqID);
                stdEntLrinvList.setBusinessID(businessID);
                stdEntLrinvList.setDatasourcefrom(dataSourceFrom);
                stdEntLrinvList.setLrname(frinvsObject.getString("name"));
                stdEntLrinvList.setInventname(frinvsObject.getString("entname"));
                stdEntLrinvList.setInvregno(frinvsObject.getString("regno"));
                stdEntLrinvList.setInventtype(frinvsObject.getString("enttype"));
                stdEntLrinvList.setInvregcap(EtlCleanRules.deleteZero(EtlCleanRules.deleteSpecialCharacters(EtlCleanRules.deletePlus(EtlCleanRules.amountDeleteComma(frinvsObject.getString("regcap"))))));//9,16,18,17
                stdEntLrinvList.setRegcapcur(frinvsObject.getString("regcapcur"));
                stdEntLrinvList.setRegstatus(frinvsObject.getString("entstatus"));
                stdEntLrinvList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvsObject.getString("candate"))));//19
                stdEntLrinvList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvsObject.getString("revdate"))));//19
                stdEntLrinvList.setRegorg(frinvsObject.getString("regorg"));

                String subconam = EtlCleanRules.amountDeleteComma(frinvsObject.getString("subconam"));//9,16,18,17
                String s0 = EtlCleanRules.deletePlus(subconam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntLrinvList.setSubconam(Double.parseDouble(s2));

                stdEntLrinvList.setCurrency(frinvsObject.getString("currency"));
                stdEntLrinvList.setFundedratio(String.valueOf(EtlCleanRules.deletePercentSign(frinvsObject.getString("fundedratio"))));//11
                stdEntLrinvList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(frinvsObject.getString("esdate"))));//19
                stdEntLrinvListList.add(stdEntLrinvList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao etlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                etlCleanWriteDao.inserStdEntLrinvListList(stdEntLrinvListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
                 /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_lrinv_list(法定代表人对外投资信息)标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntLrinvListList.clear();
                sqlSession.close();
            }
        }
    }


    /**
     * @Description: std_ent_liquidation_list 清算信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/19 20:27
     */
    private static void etlCleanStdEntLiquidationList(String elementscreditData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray liquidations = data.getJSONArray("liquidations");

        if (liquidations.size() > 0) {
            List<StdEntLiquidationList> stdEntLiquidationListList = new ArrayList<>();
            StdEntLiquidationList stdEntLiquidationList = null;
            for (Object liquidationsObj : liquidations) {
                stdEntLiquidationList = new StdEntLiquidationList();
                JSONObject liquidationsObject = (JSONObject) liquidationsObj;
                stdEntLiquidationList.setUuID(UUIDGenerator.generate());
                stdEntLiquidationList.setBusinessID(businessID);
                stdEntLiquidationList.setReqID(reqID);
                stdEntLiquidationList.setDatasourcefrom(dataSourceFrom);
                stdEntLiquidationList.setLigprincipal(liquidationsObject.getString("ligprincipal"));
                stdEntLiquidationList.setLiqmen(liquidationsObject.getString("liqmen"));
                stdEntLiquidationList.setLigst(liquidationsObject.getString("ligst"));
                stdEntLiquidationList.setLigenddate(DateUtils.strToDate(liquidationsObject.getString("ligenddate")));
                stdEntLiquidationList.setDebttranee(liquidationsObject.getString("debttranee"));
                stdEntLiquidationList.setClaimtranee(liquidationsObject.getString("claimtranee"));
                stdEntLiquidationListList.add(stdEntLiquidationList);
            }

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao etlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                etlCleanWriteDao.inserStdEntLiquidationListList(stdEntLiquidationListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_liquidation_list(清算信息)标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntLiquidationListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_invitem_list 企业对外投资信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/19 19:50
     */
    private static void etlCleanStdEntInvitemList(String elementscreditData, String businessID, String reqID, String dataSourceFrom)  {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray entinvs = data.getJSONArray("entinvs");

        if (entinvs.size() > 0) {
            List<StdEntInvitemList> stdEntInvitemListList = new ArrayList<>();
            StdEntInvitemList stdEntInvitemList = null;
            for (Object entinvsObj : entinvs) {
                stdEntInvitemList = new StdEntInvitemList();
                JSONObject entinvsObject = (JSONObject) entinvsObj;
                stdEntInvitemList.setUuID(UUIDGenerator.generate());
                stdEntInvitemList.setBusinessID(businessID);
                stdEntInvitemList.setReqID(reqID);
                stdEntInvitemList.setDatasourcefrom(dataSourceFrom);
                stdEntInvitemList.setInventname(entinvsObject.getString("entjgname"));
                stdEntInvitemList.setInvregno(entinvsObject.getString("regno"));
                stdEntInvitemList.setInventtype(entinvsObject.getString("enttype"));
                stdEntInvitemList.setInvregcap(EtlCleanRules.deleteZero(EtlCleanRules.deleteSpecialCharacters(EtlCleanRules.deletePlus(EtlCleanRules.amountDeleteComma(entinvsObject.getString("regcap"))))));//9,16,18,17
                stdEntInvitemList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvsObject.getString("candate"))));//19
                stdEntInvitemList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvsObject.getString("revdate"))));//19
                stdEntInvitemList.setRegstatus(entinvsObject.getString("entstatus"));
                stdEntInvitemList.setRegorg(entinvsObject.getString("regorg"));

                String subConam = EtlCleanRules.amountDeleteComma(entinvsObject.getString("subconam"));//9,16,18,17
                String s0 = EtlCleanRules.deletePlus(subConam);
                String s1 = EtlCleanRules.deleteSpecialCharacters(s0);
                String s2 = EtlCleanRules.deleteZero(s1);
                stdEntInvitemList.setSubconam(Double.parseDouble(s2));

                stdEntInvitemList.setCurrency(entinvsObject.getString("congrocur"));
                stdEntInvitemList.setFundedratio(String.valueOf(EtlCleanRules.deletePercentSign(entinvsObject.getString("fundedratio"))));//11.去掉%
                stdEntInvitemList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entinvsObject.getString("esdate"))));//19
                stdEntInvitemList.setInvlrname(entinvsObject.getString("name"));
                stdEntInvitemListList.add(stdEntInvitemList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao etlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                etlCleanWriteDao.inserStdEntInvitemListList(stdEntInvitemListList);
                sqlSession.commit();
               /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_invitem_list(企业对外投资信息) 标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            } finally {
                stdEntInvitemListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_filiation_list 分支机构
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/19 19:10
     */
    private static void etlCleanStdEntFiliationList(String elementscreditData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray filiations = data.getJSONArray("filiations");

        if (filiations.size() > 0) {
            List<StdEntFiliationList> stdEntFiliationListList = new ArrayList<>();
            StdEntFiliationList stdEntFiliationList = null;
            for (Object filiationsObj : filiations) {
                stdEntFiliationList = new StdEntFiliationList();
                JSONObject filiationsObject = (JSONObject) filiationsObj;
                stdEntFiliationList.setUuID(UUIDGenerator.generate());
                stdEntFiliationList.setBusinessID(businessID);
                stdEntFiliationList.setReqID(reqID);
                stdEntFiliationList.setDatasourcefrom(dataSourceFrom);
                stdEntFiliationList.setBrname(filiationsObject.getString("brname"));
                stdEntFiliationList.setBrregno(filiationsObject.getString("brregno"));
                stdEntFiliationListList.add(stdEntFiliationList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao etlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                etlCleanWriteDao.inserStdEntFiliationListList(stdEntFiliationListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_filiation_list(分支机构) 标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntFiliationListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_caseinfo_list  行政处罚
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/19 16:49
     */
    private static void etlCleanStdEntCaseInfoList(String elementscreditData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray entcasebaseinfos = data.getJSONArray("entcasebaseinfos");

        if (entcasebaseinfos.size() > 0) {
            List<StdEntCaseInfoList> stdEntCaseInfoListList = new ArrayList<>();
            StdEntCaseInfoList stdEntCaseInfoList = null;
            for (Object entcasebaseinfosObj : entcasebaseinfos) {
                stdEntCaseInfoList = new StdEntCaseInfoList();
                JSONObject entcasebaseinfosObject = (JSONObject) entcasebaseinfosObj;
                stdEntCaseInfoList.setUuID(UUIDGenerator.generate());
                stdEntCaseInfoList.setBusinessID(businessID);
                stdEntCaseInfoList.setReqID(reqID);
                stdEntCaseInfoList.setDatasourcefrom(dataSourceFrom);
                stdEntCaseInfoList.setPendecissdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(entcasebaseinfosObject.getString("pendecissdate"))));//19
                stdEntCaseInfoList.setPenauth(entcasebaseinfosObject.getString("penauth"));
                stdEntCaseInfoList.setIllegfact(entcasebaseinfosObject.getString("illegacttype"));
                stdEntCaseInfoList.setPentype(entcasebaseinfosObject.getString("pentype"));
                stdEntCaseInfoList.setPendecno(entcasebaseinfosObject.getString("pendecno"));
                stdEntCaseInfoListList.add(stdEntCaseInfoList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao etlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                etlCleanWriteDao.inserStdEntCaseInfoListList(stdEntCaseInfoListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
                 /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_caseinfo_list(行政处罚)标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntCaseInfoListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_basic_list  企业照面信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/19 15:30
     */
    private static void etlCleanStdEntBasicList(String elementscreditData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONObject basic = data.getJSONObject("basic");

        if (basic.size() > 0) {
            List<StdEntBasicList> stdEntBasicListList = new ArrayList<>();
            StdEntBasicList stdEntBasicList = new StdEntBasicList();
            stdEntBasicList.setUuID(UUIDGenerator.generate());
            stdEntBasicList.setBusinessID(businessID);
            stdEntBasicList.setReqID(reqID);
            stdEntBasicList.setDatasourcefrom(dataSourceFrom);
            stdEntBasicList.setEntname(basic.getString("entname"));
            stdEntBasicList.setCreditcode(basic.getString("creditcode"));
            stdEntBasicList.setRegno(basic.getString("regno"));
            stdEntBasicList.setEnttype(basic.getString("enttype"));
            stdEntBasicList.setLrname(basic.getString("frname"));
            stdEntBasicList.setRegcap(basic.getDouble("regcap"));
            stdEntBasicList.setRegcapcur(basic.getString("regcapcur"));
            stdEntBasicList.setReccap(basic.getDouble("reccap"));
            stdEntBasicList.setEsdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basic.getString("esdate")))); //19
            stdEntBasicList.setOpenfrom(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basic.getString("opfrom"))));//19
            stdEntBasicList.setOpento(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basic.getString("opto"))));//19
            stdEntBasicList.setRegorg(basic.getString("regorg"));
            stdEntBasicList.setApprdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basic.getString("apprdate"))));//19
            stdEntBasicList.setAbuitem(basic.getString("abuitem"));
            stdEntBasicList.setIndustrycode(basic.getString("industrycocode"));
            stdEntBasicList.setIndustryname(basic.getString("industryconame"));
            stdEntBasicList.setCandate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basic.getString("candate"))));//19
            stdEntBasicList.setRevdate(DateUtils.strToDate(EtlCleanRules.dateFormatCleaning(basic.getString("revdate"))));//19
            stdEntBasicList.setAncheyear(EtlCleanRules.dateFormatCleaning(basic.getString("ancheyear")));//19
            stdEntBasicList.setOriregno(basic.getString("oriregno"));
            stdEntBasicListList.add(stdEntBasicList);

            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao ysetlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                ysetlCleanWriteDao.inserStdEntBasicListList(stdEntBasicListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
                 /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_basic_list （企业照面信息）标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntBasicListList.clear();
                sqlSession.close();
            }
        }
    }

    /**
     * @Description: std_ent_alter_list 企业历史变更信息
     * @param: [amarsoftData, businessID, reqID]
     * @return: void
     * @author:Yxx
     * @Date: 2018/12/19 14:21
     */
    public static void etlCleanStdEntAlterList(String elementscreditData, String businessID, String reqID, String dataSourceFrom) {
        //将Json字符串转为Json对象
        JSONObject stringJsonObject = JSONObject.parseObject(elementscreditData);
        JSONObject data = stringJsonObject.getJSONObject("data");
        JSONArray alters = data.getJSONArray("alters");

        if (alters.size() > 0) {
            List<StdEntAlterList> stdEntAlterListList = new ArrayList<>();
            StdEntAlterList stdEntAlterList = null;
            for (Object alterObj : alters) {
                stdEntAlterList = new StdEntAlterList();
                JSONObject alterObject = (JSONObject) alterObj;
                stdEntAlterList.setUuID(UUIDGenerator.generate());
                stdEntAlterList.setBusinessID(businessID);
                stdEntAlterList.setReqID(reqID);
                stdEntAlterList.setDatasourcefrom(dataSourceFrom);
                stdEntAlterList.setAltaf(alterObject.getString("altaf"));
                stdEntAlterList.setAltbe(alterObject.getString("altbe"));
                stdEntAlterList.setAltdate(DateUtils.strToDate(alterObject.getString("altdate")));//变更信息
                stdEntAlterList.setAltitem(alterObject.getString("altitem"));
                stdEntAlterListList.add(stdEntAlterList);
            }
            SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
            ETLCleanWriteDao etlCleanWriteDao = sqlSession.getMapper(ETLCleanWriteDao.class);
            try {
                etlCleanWriteDao.inserStdEntAlterListList(stdEntAlterListList);
                sqlSession.commit();
            } catch (Exception e) {
                e.printStackTrace();
                sqlSession.rollback();
            /*throw new ServiceException(ErrorCodeIDE.VCloudeSpark.Sql_Insert_ERROR
                   + ",std_ent_alter_list(企业历史变更信息)标准表入库失败！"
                   + System.getProperty("line.separator")
                   , e);*/
            } finally {
                stdEntAlterListList.clear();
                sqlSession.close();
            }
        }
    }

}
