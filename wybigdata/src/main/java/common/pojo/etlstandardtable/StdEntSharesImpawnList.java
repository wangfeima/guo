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
 * @description:json kong
 */
public class StdEntSharesImpawnList implements Serializable {
    private String uuID;

    private String reqID;

    private String businessID;

    private String imporg;

    private String imporgtype;

    private Double impam;

    private Date imponrecdate;

    private String impexaeep;

    private Date impsandate;

    private Date impto;

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

    public String getImporg() {
        return imporg;
    }

    public void setImporg(String imporg) {
        this.imporg = imporg == null ? null : imporg.trim();
    }

    public String getImporgtype() {
        return imporgtype;
    }

    public void setImporgtype(String imporgtype) {
        this.imporgtype = imporgtype == null ? null : imporgtype.trim();
    }

    public Double getImpam() {
        return impam;
    }

    public void setImpam(Double impam) {
        this.impam = impam;
    }

    public Date getImponrecdate() {
        return imponrecdate;
    }

    public void setImponrecdate(Date imponrecdate) {
        this.imponrecdate = imponrecdate;
    }

    public String getImpexaeep() {
        return impexaeep;
    }

    public void setImpexaeep(String impexaeep) {
        this.impexaeep = impexaeep == null ? null : impexaeep.trim();
    }

    public Date getImpsandate() {
        return impsandate;
    }

    public void setImpsandate(Date impsandate) {
        this.impsandate = impsandate;
    }

    public Date getImpto() {
        return impto;
    }

    public void setImpto(Date impto) {
        this.impto = impto;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }

}