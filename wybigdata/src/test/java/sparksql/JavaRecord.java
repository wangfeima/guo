package sparksql;

import java.util.Iterator;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/11/24 21:59
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: vcloude01
 * @pachage_name: sparksql
 * @description:
 */
public class JavaRecord implements java.io.Serializable {
    private String word;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }
}
