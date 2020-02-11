package com.dfwy.online.sparkstreamingtask.business.basics;

import common.pojo.applicantinformation.ApplicantInformationTO;
import common.pojo.quotas.QuotasDataValueEntity;

import java.util.Vector;

public interface IdeQuotasBusiService {
    /**
     *
     * @description: Input description
     * @Autor: FangRen
     * @param
     * @time:2018年7月23日 上午10:15:58
     */
    Vector<QuotasDataValueEntity> execQuotas(ApplicantInformationTO informationTO, String execStage, String execPri);
}
