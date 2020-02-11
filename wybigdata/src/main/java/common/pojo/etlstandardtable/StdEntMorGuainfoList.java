package common.pojo.etlstandardtable;

import java.io.Serializable;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/18 13:38
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: common.pojo.etlstandardtable
 * @description://json kong
 */
public class StdEntMorGuainfoList implements Serializable {
    private String uuID;

    private String reqID;

    private String businessID;

    private String morregID;

    private String guaname;

    private String quan;

    private Double guavalue;

    private String createtime;

    //数据来源：安硕amarsoft，元素elementscredit，中诚信ccx credit technology
    private String datasourcefrom;

    public String getDatasourcefrom() {
        return datasourcefrom;
    }

    public void setDatasourcefrom(String datasourcefrom) {
        this.datasourcefrom = datasourcefrom == null ? null : datasourcefrom.trim();
    }

    public String getUuID() {
        return uuID;
    }

    public void setUuID(String uuID) {
        this.uuID = uuID == null ? null : uuID.trim();
    }

    public String getReqID() {
        return reqID;
    }

    public void setReqID(String reqID) {
        this.reqID = reqID == null ? null : reqID.trim();
    }

    public String getBusinessID() {
        return businessID;
    }

    public void setBusinessID(String businessID) {
        this.businessID = businessID == null ? null : businessID.trim();
    }

    public String getMorregID() {
        return morregID;
    }

    public void setMorregID(String morregID) {
        this.morregID = morregID == null ? null : morregID.trim();
    }

    public String getGuaname() {
        return guaname;
    }

    public void setGuaname(String guaname) {
        this.guaname = guaname == null ? null : guaname.trim();
    }

    public String getQuan() {
        return quan;
    }

    public void setQuan(String quan) {
        this.quan = quan == null ? null : quan.trim();
    }

    public Double getGuavalue() {
        return guavalue;
    }

    public void setGuavalue(Double guavalue) {
        this.guavalue = guavalue;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

}