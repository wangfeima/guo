package com.dfwy.online.sparkstreamingtask.dao;

import common.pojo.legalbean.LegalDataAdded;
import common.utils.exception.DaoException;

import java.util.List;

public interface JudgeAnynasisWriteDao {

    /**
     * @Description: 得到司法解析数据结果入库
     * @param: [list]
     * @return: void
     * @author: Yxx
     * @Date: 2018/12/6 14:06
     */
    void insertLegalDataAddedColumn(List<LegalDataAdded> list) throws DaoException;


}
