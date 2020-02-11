package common.pojo.quotas;

import java.io.Serializable;

public class QuatesDetailEntity implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	// 指标代码
    private String quotasCode;
    // 指标名称
    private String quotasName;
    // 指标值
    private String quotasValue;
    // 指标结果
    private String resultValue;
    
    
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
	public String getResultValue() {
		return resultValue;
	}
	public void setResultValue(String resultValue) {
		this.resultValue = resultValue;
	}
    

}
