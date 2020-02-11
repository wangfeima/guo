package common.pojo.etlstandardtable;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/18 13:38
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: common.pojo.etlstandardtable
 * @description:
 */
public class StdEntSharesfrostList implements Serializable {
    private String uuID;

    private String reqID;

    private String businessID;

    private String frodocno;

    private String froauth;

    private Date frofrom;

    private Date froto;

    private Double froam;

    private String thawauth;

    private String thawdocno;

    private Date thawdate;

    private String createtime;

    private String thawcomment;

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

    public String getFrodocno() {
        return frodocno;
    }

    public void setFrodocno(String frodocno) {
        this.frodocno = frodocno == null ? null : frodocno.trim();
    }

    public String getFroauth() {
        return froauth;
    }

    public void setFroauth(String froauth) {
        this.froauth = froauth == null ? null : froauth.trim();
    }

    public Date getFrofrom() {
        return frofrom;
    }

    public void setFrofrom(Date frofrom) {
        this.frofrom = frofrom;
    }

    public Date getFroto() {
        return froto;
    }

    public void setFroto(Date froto) {
        this.froto = froto;
    }

    public Double getFroam() {
        return froam;
    }

    public void setFroam(Double froam) {
        this.froam = froam;
    }

    public String getThawauth() {
        return thawauth;
    }

    public void setThawauth(String thawauth) {
        this.thawauth = thawauth == null ? null : thawauth.trim();
    }

    public String getThawdocno() {
        return thawdocno;
    }

    public void setThawdocno(String thawdocno) {
        this.thawdocno = thawdocno == null ? null : thawdocno.trim();
    }

    public Date getThawdate() {
        return thawdate;
    }

    public void setThawdate(Date thawdate) {
        this.thawdate = thawdate;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getThawcomment() {
        return thawcomment;
    }

    public void setThawcomment(String thawcomment) {
        this.thawcomment = thawcomment == null ? null : thawcomment.trim();
    }
}