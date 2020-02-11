package common.pojo.jsonfirstrootbean;

import java.io.Serializable;

/**
 *
 */
public class JsonFirstRootBean implements Serializable {

    //数据信息
    private String amarsoftData;
    //可用性
    private String available;
    //银行公共指标池
    private String bankQuotaspool;
    //业务ID
    private String businessID;

    private String entCreditID;

    private String entName;

    private String indName;

    private String productCode;

    private String reqID;

    private String uuid;


    public String getAmarsoftData() {
        return amarsoftData;
    }

    public void setAmarsoftData(String amarsoftData) {
        this.amarsoftData = amarsoftData;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getBankQuotaspool() {
        return bankQuotaspool;
    }

    public void setBankQuotaspool(String bankQuotaspool) {
        this.bankQuotaspool = bankQuotaspool;
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID;
    }

    public String getEntCreditID() {
        return entCreditID;
    }

    public void setEntCreditID(String entCreditID) {
        this.entCreditID = entCreditID;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    public String getIndName() {
        return indName;
    }

    public void setIndName(String indName) {
        this.indName = indName;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "JsonFirstRootBean{" +
                "amarsoftData='" + amarsoftData + '\'' +
                ", available='" + available + '\'' +
                ", bankQuotaspool='" + bankQuotaspool + '\'' +
                ", businessID='" + businessID + '\'' +
                ", entCreditID='" + entCreditID + '\'' +
                ", entName='" + entName + '\'' +
                ", indName='" + indName + '\'' +
                ", productCode='" + productCode + '\'' +
                ", reqID='" + reqID + '\'' +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
