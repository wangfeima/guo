
package common.pojo.applicantinformation;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 申请信息
 * @author FSP
 *
 */
public class ApplicantInformationTO implements Serializable {

	private static final long serialVersionUID = -693198237264643446L;

	//主键uuid
	private String uuid;

	//业务申请编号
	private String businessID;
	
	//申请编号ID 由我方生成
	private String reqID;
	
	//申请人姓名
	private String indName;
	
	//申请人证件类型  0:身份证 
	private Integer indCertType;
	
	//申请人证件号码
	private String indCertID;
	
	//企业名称
	private String entName;
	
	//社会统一信用代码
	private String entCreditID;
	
	//工商注册号
	private String entRegID;
		
	//纳税识别号
	private String entTaxID;
		
	//贷款申请金额
	private Integer applyAmount;
		
	//申请期限
	private Integer applyTerm;
		
	//渠道客户编号
	private String channelCode;
	
	//产品Code
	private String productCode;
	
	//是否有效  0：无效  1：有效 （同一个businessID只有一个有效）
	private Integer available;
	
	//创建时间
	private String createTime;
	
	//手机号
	private String indPhoneID;
	
	//营销人员编号
	private String applyMarketer;
	
	//业务状态
	private String businessStatus; 
	
	//执行阶段
	private Integer executeStage;
	
	//版本Code
	private String versionCode;
	
	//1:取数中 2：成功 3：失败
	private Integer interfaceStatus;
	
	//婚姻状态
	private String maritalState;
	
	//办理机构代码
	private String applyDepart;
	
	//银行编号
	private String bankNo;
	
	//产品类型 01：新增 02：续贷 03 预授信
	private String productType;
	
	//银行公共指标池
	private String bankQuotaspool;
	
	//银行安硕工商司法数据指标真实值
	private String bankQuotasvalue;

	//数据来源：安硕amarsoft，元素elementscredit，中诚信ccx credit technology
	private String dataSourceFrom;
	//安硕工商司法数据原始报文
	private String amarsoftData;
	//元素数据
	private String elementscreditData;
	//中诚信数据
	private String ccxData;

	public String getCcxData() {
		return ccxData;
	}

	public void setCcxData(String ccxData) {
		this.ccxData = ccxData;
	}

	public String getElementscreditData() {
		return elementscreditData;
	}

	public void setElementscreditData(String elementscreditData) {
		this.elementscreditData = elementscreditData;
	}

	public String getDataSourceFrom() {
		return dataSourceFrom;
	}

	public void setDataSourceFrom(String dataSourceFrom) {
		this.dataSourceFrom = dataSourceFrom;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}

	public String getReqID() {
		return reqID;
	}

	public void setReqID(String reqID) {
		this.reqID = reqID;
	}

	public String getIndCertID() {
		return indCertID;
	}

	public void setIndCertID(String indCertID) {
		this.indCertID = indCertID;
	}

	public String getBusinessID() {
		return businessID;
	}

	public void setBusinessID(String businessID) {
		this.businessID = businessID;
	}

	public String getIndName() {
		return indName;
	}

	public Integer getApplyTerm() {
		return applyTerm;
	}

	public void setApplyTerm(Integer applyTerm) {
		this.applyTerm = applyTerm;
	}

	public void setIndName(String indName) {
		this.indName = indName;
	}

	public Integer getIndCertType() {
		return indCertType;
	}

	public void setIndCertType(Integer indCertType) {
		this.indCertType = indCertType;
	}

	public String getEntName() {
		return entName;
	}

	public void setEntName(String entName) {
		this.entName = entName;
	}

	public String getEntCreditID() {
		return entCreditID;
	}

	public void setEntCreditID(String entCreditID) {
		this.entCreditID = entCreditID;
	}

	public String getEntRegID() {
		return entRegID;
	}

	public void setEntRegID(String entRegID) {
		this.entRegID = entRegID;
	}

	public String getEntTaxID() {
		return entTaxID;
	}

	public void setEntTaxID(String entTaxID) {
		this.entTaxID = entTaxID;
	}

	public Integer getApplyAmount() {
		return applyAmount;
	}

	public void setApplyAmount(Integer applyAmount) {
		this.applyAmount = applyAmount;
	}

	 

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	
	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
	public Integer getAvailable() {
		return available;
	}

	public void setAvailable(Integer available) {
		this.available = available;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getIndPhoneID() {
		return indPhoneID;
	}

	public void setIndPhoneID(String indPhoneID) {
		this.indPhoneID = indPhoneID;
	}

	public String getApplyDepart() {
		return applyDepart;
	}

	public void setApplyDepart(String applyDepart) {
		this.applyDepart = applyDepart;
	}

	public String getApplyMarketer() {
		return applyMarketer;
	}

	public void setApplyMarketer(String applyMarketer) {
		this.applyMarketer = applyMarketer;
	}

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public Integer getExecuteStage() {
		return executeStage;
	}

	public void setExecuteStage(Integer executeStage) {
		this.executeStage = executeStage;
	}

	public Integer getInterfaceStatus() {
		return interfaceStatus;
	}

	public void setInterfaceStatus(Integer interfaceStatus) {
		this.interfaceStatus = interfaceStatus;
	}

	public String getMaritalState() {
		return maritalState;
	}

	public void setMaritalState(String maritalState) {
		this.maritalState = maritalState;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getBankQuotaspool() {
		return bankQuotaspool;
	}

	public void setBankQuotaspool(String bankQuotaspool) {
		this.bankQuotaspool = bankQuotaspool;
	}

	public String getBankQuotasvalue() {
		return bankQuotasvalue;
	}

	public void setBankQuotasvalue(String bankQuotasvalue) {
		this.bankQuotasvalue = bankQuotasvalue;
	}

	public String getAmarsoftData() {
		return amarsoftData;
	}

	public void setAmarsoftData(String amarsoftData) {
		this.amarsoftData = amarsoftData;
	}

}
