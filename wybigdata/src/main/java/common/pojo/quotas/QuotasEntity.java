package common.pojo.quotas;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @description: 指标基础信息实体类
 * @author: FangRen
 * @version V2.0 
 * @date: 2018年6月22日 下午2:06:21
 * @copyright©2018东方微银网络信息（北京）有限公司 
 * @fileName:com.ide.pojo.QuotasEntity.java
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuotasEntity extends BaseEntity{
	private static final long serialVersionUID = 1L;
	// 申请编号id 由我方生成
    private String reqID;
	// 业务申请编号
    private String businessID;
    // 指标代码
    private String quotasCode;
    // 指标名称
    private String quotasName;
    // 执行方法
    private String execMethod;
    // 执行sql
    private String execSql;
    // 执行阶段
    private String execStage;
    // 执行优先级
    private String execPri;
    // 指标类型（连续 Continuous 离散 Nominal）
    private String quotasType;
    // 数据来源
    private String dataSources;
    // 产品Code
    private String productCode;
    // 银行编号
    private String bankNo;
    

    public String getReqID() {
		return reqID;
	}

	public void setReqID(String reqID) {
		this.reqID = reqID;
	}
	
	public String getBusinessID() {
		return businessID;
	}

	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	public String getQuotasCode() {
        return quotasCode;
    }

    public void setQuotasCode(String quotasCode) {
        this.quotasCode = quotasCode;
    }

    public String getQuotasName() {
        return quotasName;
    }

    public void setQuotasName(String quotasName) {
        this.quotasName = quotasName;
    }

    public String getExecMethod() {
        return execMethod;
    }

    public void setExecMethod(String execMethod) {
        this.execMethod = execMethod;
    }

    public String getExecSql() {
        return execSql;
    }

    public void setExecSql(String execSql) {
        this.execSql = execSql;
    }

    public String getExecStage() {
        return execStage;
    }

    public void setExecStage(String execStage) {
        this.execStage = execStage;
    }

    public String getExecPri() {
		return execPri;
	}

	public void setExecPri(String execPri) {
		this.execPri = execPri;
	}

    public String getQuotasType() {
        return quotasType;
    }

    public void setQuotasType(String quotasType) {
        this.quotasType = quotasType;
    }

    public String getDataSources() {
        return dataSources;
    }

    public void setDataSources(String dataSources) {
        this.dataSources = dataSources;
    }

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}
   
}