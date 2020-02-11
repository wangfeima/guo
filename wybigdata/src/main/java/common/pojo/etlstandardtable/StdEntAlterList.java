package common.pojo.etlstandardtable;

import java.io.Serializable;
import java.util.Date;


/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/17 16:00
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: common.pojo.etlstandardtable
 * @description:企业历史变更信息
 */
public class StdEntAlterList implements Serializable {

    private String uuID;

    private String reqID;

    private String businessID;

    private String altitem;

    private String altbe;

    private Date altdate;

    private String createtime;

    private String altaf;

    //数据来源：安硕amarsoft，元素elementscredit，中诚信ccx credit technology
    private String datasourcefrom;

    public String getDatasourcefrom() {
        return datasourcefrom;
    }

    public void setDatasourcefrom(String datasourcefrom) {
        this.datasourcefrom = datasourcefrom == null ? null : datasourcefrom.trim();
    }

    public String getAltitem() {
        return altitem;
    }

    public void setAltitem(String altitem) {
        this.altitem = altitem == null ? null : altitem.trim();
    }

    public String getAltbe() {
        return altbe;
    }

    public void setAltbe(String altbe) {
        this.altbe = altbe == null ? null : altbe.trim();
    }

    public String getAltaf() {
        return altaf;
    }

    public void setAltaf(String altaf) {
        this.altaf = altaf == null ? null : altaf.trim();
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

    public Date getAltdate() {
        return altdate;
    }

    public void setAltdate(Date altdate) {
        this.altdate = altdate;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }
}