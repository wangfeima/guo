package com.dfwy.online.sparkstreamingtask.jsondemo;

import java.io.Serializable;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/14 11:05
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.jsondemo
 * @description:
 */
public class JsonDemoObject implements Serializable {
    private String uuid;
    private String reqid;
    private String entName;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getReqid() {
        return reqid;
    }

    public void setReqid(String reqid) {
        this.reqid = reqid;
    }

    public String getEntName() {
        return entName;
    }

    public void setEntName(String entName) {
        this.entName = entName;
    }

    @Override
    public String toString() {
        return "JsonDemoObject{" +
                "uuid='" + uuid + '\'' +
                ", reqid='" + reqid + '\'' +
                ", entName='" + entName + '\'' +
                '}';
    }
}
