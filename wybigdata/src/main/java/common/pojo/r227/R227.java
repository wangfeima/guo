/**
  * Copyright 2018 bejson.com 
  */
package common.pojo.r227;

import java.io.Serializable;
import java.util.List;

/**
 * Auto-generated: 2018-11-01 15:37:7
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class R227 implements Serializable {

    private String code;
    //private List<R227Data> r227DataList;
    private String data;
    public  String getData() {
        return data;
    }
    public void   setData(String data) {
        this.data = data;
    }
    private String msg;
    public void setCode(String code) {
         this.code = code;
     }
     public String getCode() {
         return code;
     }

    /*public List<R227Data> getR227DataList() {
        return r227DataList;
    }*/

    /*public void setR227DataList(List<R227Data> r227DataList) {
        this.r227DataList = r227DataList;
    }*/

    public void setMsg(String msg) {
         this.msg = msg;
     }
     public String getMsg() {
         return msg;
     }

}