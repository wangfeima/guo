package common.pojo.etlstandardtable;

import java.io.Serializable;

/**
 * Created by Administrator on 2018/12/18 0018.
 */
public class StdEntRyposperList implements Serializable {

    private String uuID;
    private String reqID;
    private String businessID;
    private String ryname;
    private String entname;
    private String regno;
    private String enttype;
    private String regcap;
    private String regcapcur;
    private String regstatus;
    private String position;
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

    public String getRyname() {
        return ryname;
    }

    public void setRyname(String ryname) {
        this.ryname = ryname == null ? null : ryname.trim();
    }

    public String getEntname() {
        return entname;
    }

    public void setEntname(String entname) {
        this.entname = entname == null ? null : entname.trim();
    }

    public String getRegno() {
        return regno;
    }

    public void setRegno(String regno) {
        this.regno = regno == null ? null : regno.trim();
    }

    public String getEnttype() {
        return enttype;
    }

    public void setEnttype(String enttype) {
        this.enttype = enttype == null ? null : enttype.trim();
    }

    public String getRegcap() {
        return regcap;
    }

    public void setRegcap(String regcap) {
        this.regcap = regcap == null ? null : regcap.trim();
    }

    public String getRegcapcur() {
        return regcapcur;
    }

    public void setRegcapcur(String regcapcur) {
        this.regcapcur = regcapcur == null ? null : regcapcur.trim();
    }

    public String getRegstatus() {
        return regstatus;
    }

    public void setRegstatus(String regstatus) {
        this.regstatus = regstatus == null ? null : regstatus.trim();
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position == null ? null : position.trim();
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime == null ? null : createtime.trim();
    }
}
