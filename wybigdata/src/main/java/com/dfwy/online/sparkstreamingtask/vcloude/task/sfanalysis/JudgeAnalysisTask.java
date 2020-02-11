package com.dfwy.online.sparkstreamingtask.vcloude.task.sfanalysis;


import com.dfwy.online.sparkstreamingtask.dao.JudgeAnynasisReadDao;
import com.dfwy.online.sparkstreamingtask.dao.JudgeAnynasisWriteDao;
import common.pojo.legalbean.LegalDataAdded;
import common.pojo.legalbean.LegalStructuredData;
import common.utils.sqlsessionfactoryutil.SqlSessionFactoryUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class JudgeAnalysisTask {
    public  static void judgeParseData() throws SQLException, IOException {

        SqlSession sqlSession = SqlSessionFactoryUtil.openSqlSession();
        JudgeAnynasisReadDao judgeAnynasisMapper = sqlSession.getMapper(JudgeAnynasisReadDao.class);
        JudgeAnynasisWriteDao mapper = sqlSession.getMapper(JudgeAnynasisWriteDao.class);
        //1.初始化司法解析引擎正则式
        JudicialAnalysisImpl.init_regex(judgeAnynasisMapper);
        JudicialAnalysisImpl judicialAnalysis = new JudicialAnalysisImpl();
        //1.1读取标准表数据
        List<LegalStructuredData> legalStructuredDatas = judgeAnynasisMapper.getLegalStructuredData();
        for(LegalStructuredData legalStructuredData : legalStructuredDatas){
            //1.2获取固定数据
            String reqID = legalStructuredData.getReq_ID();
            String businessID = legalStructuredData.getBusinessID();
            String dataDatasourceFrom = legalStructuredData.getDatasourcefrom();
            //2.解析诉讼和结构化数据wy_legal_data_structured到新增司法数据表wy_std_legal_data_added
            List<LegalDataAdded> legalDataAddeds = judicialAnalysis.anynasisAndInsert(legalStructuredData, reqID, businessID,dataDatasourceFrom);
            mapper.insertLegalDataAddedColumn(legalDataAddeds);
            //提交
            sqlSession.commit();
        }
        //关闭
        sqlSession.close();
    }
}
