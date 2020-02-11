package common.pojo.quotas;

import java.io.Serializable;

/**
 * 
 * @description: 指标真实值实体类
 * @author: FangRen
 * @version V2.0 
 * @date: 2018年6月22日 下午2:13:39
 * @copyright©2018东方微银网络信息（北京）有限公司 
 * @fileName:com.ide.pojo.QuotasDataValueEntity.java
 */
public class QuotasDataValueEntity  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 主键id
    private String qdvId;
    // 申请id
    private String reqId;
	// 业务申请编号
    private String businessID;
    // 指标代码
    private String quotasCode;
    // 指标名称
    private String quotasName;
    // 指标真实值
    private String quotasValue;
    // 异常编码
    private String exceptionCode;
    // 是否可用
    private String enabled;
    // 创建时间
    private String createtime;

    public String getQdvId() {
        return qdvId;
    }

    public void setQdvId(String qdvId) {
        this.qdvId = qdvId;
    }

    public String getReqId() {
        return reqId;
    }

    public void setReqId(String reqId) {
        this.reqId = reqId;
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

    public String getQuotasValue() {
        return quotasValue;
    }

    public void setQuotasValue(String quotasValue) {
        this.quotasValue = quotasValue;
    }

    public String getEnabled() {
        return enabled;
    }

    public void setEnabled(String enabled) {
        this.enabled = enabled;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
    
	public String getExceptionCode() {
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}

	@Override
	public String toString() {
		return "QuotasDataValueEntity [qdvId=" + qdvId + ", reqId=" + reqId + ", quotasCode=" + quotasCode
				+ ", quotasName=" + quotasName + ", quotasValue=" + quotasValue + ", exceptionCode=" + exceptionCode
				+ ", enabled=" + enabled + ", createtime=" + createtime + "]";
	}
}