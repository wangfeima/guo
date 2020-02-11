package common.pojo.quotas;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Description: 模型模块指标规则关联表
 * Versions: 2.0 			
 * FileName: QuotasRuleRelationEntity.java
 * Company: dfwyBank
 * @Author: YanTianLin
 * Created: 2018年6月22日下午5:33:22
 * Department: 深圳IT研发部门  
 */
public class QuotasRuleRelationEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String reqId;								//申请id
	private String modelCode;					//模型代码
	private String moduleCode;					//模块代码
	private String typeCode;						//模块类型代码
	private String quotasCode;					//指标代码
	private String quotasName;					//指标名称
	private BigDecimal quotasWeight;	//指标权重
	private String operType;						//计算类型
	private String quotasValue;					//指标值
	private String exceptionCode;			//异常编码
	private String ruleCode;							//规则代码
	private String ruleName;						//规则名称
	private String execScript;						//执行脚本
	private String detail;								//规则描述
	
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	
	public String getModelCode() {
		return modelCode;
	}
	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}
	
	public String getModuleCode() {
		return moduleCode;
	}
	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}
	
	public String getTypeCode() {
		return typeCode;
	}
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
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
	
	public BigDecimal getQuotasWeight() {
		return quotasWeight;
	}
	public void setQuotasWeight(BigDecimal quotasWeight) {
		this.quotasWeight = quotasWeight;
	}
	
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	
	public String getQuotasValue() {
		return quotasValue;
	}
	public void setQuotasValue(String quotasValue) {
		this.quotasValue = quotasValue;
	}
	
	public String getExceptionCode() {
		return exceptionCode;
	}
	public void setExceptionCode(String exceptionCode) {
		this.exceptionCode = exceptionCode;
	}
	
	public String getRuleCode() {
		return ruleCode;
	}
	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}
	
	public String getRuleName() {
		return ruleName;
	}
	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}
	
	public String getExecScript() {
		return execScript;
	}
	public void setExecScript(String execScript) {
		this.execScript = execScript;
	}
	
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
