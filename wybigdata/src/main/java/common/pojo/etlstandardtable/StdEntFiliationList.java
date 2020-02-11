package common.pojo.etlstandardtable;

import java.io.Serializable;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/18 13:38
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: common.pojo.etlstandardtable
 * @description:json 空
 */
public class StdEntFiliationList implements Serializable {
    private String uuID;

    private String reqID;

    private String businessID;

    private String brname;

    private String brregno;

    private String brprincipal;

    private String braddr;

    private String createtime;

    private String cbuitem;

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

    public String getBrname() {
        return brname;
    }

    public void setBrname(String brname) {
        this.brname = brname == null ? null : brname.trim();
    }

    public String getBrregno() {
        return brregno;
    }

    public void setBrregno(String brregno) {
        this.brregno = brregno == null ? null : brregno.trim();
    }

    public String getBrprincipal() {
        return brprincipal;
    }

    public void setBrprincipal(String brprincipal) {
        this.brprincipal = brprincipal == null ? null : brprincipal.trim();
    }

    public String getBraddr() {
        return braddr;
    }

    public void setBraddr(String braddr) {
        this.braddr = braddr == null ? null : braddr.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

    public String getCbuitem() {
        return cbuitem;
    }

    public void setCbuitem(String cbuitem) {
        this.cbuitem = cbuitem == null ? null : cbuitem.trim();
    }
}