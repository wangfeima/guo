/**
  * Copyright 2018 bejson.com 
  */
package common.pojo.r231;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-11-01 15:37:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class R231 implements Serializable {

    private String code;
    private List<R231Data> r231DataList;
    private String msg;
    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    public List<R231Data> getR231DataList() {
        return r231DataList;
    }

    public void setR231DataList(List<R231Data> r231DataList) {
        this.r231DataList = r231DataList;
    }

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

}