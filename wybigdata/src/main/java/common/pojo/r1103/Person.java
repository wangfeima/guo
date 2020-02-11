/**
  * Copyright 2018 bejson.com 
  */
package common.pojo.r1103;

import java.io.Serializable;

/**
 * Auto-generated: 2018-11-02 9:35:52
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Person implements Serializable {

    private String name;
    private String position;
    private String sex;
    public void setName(String name) {
         this.name = name;
     }
     public String getName() {
         return name;
     }

    public void setPosition(String position) {
         this.position = position;
     }
     public String getPosition() {
         return position;
     }

    public void setSex(String sex) {
         this.sex = sex;
     }
     public String getSex() {
         return sex;
     }

}