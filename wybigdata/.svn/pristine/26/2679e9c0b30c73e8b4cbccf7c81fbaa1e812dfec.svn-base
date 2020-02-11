package log;

import common.utils.log.Log4jUtil;
import org.apache.log4j.Logger;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/10 19:32
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: log
 * @description:
 */
public class logtest1 {
    private static Logger logger = Logger.getLogger(logtest1.class.getName());
    public static void main(String[] args) {
//        Log4jUtil.debug("debug2信息");
//        Log4jUtil.info("info2信息");
//        Log4jUtil.warn("warn2信息");
//        Log4jUtil.error("error2信息");
        int sum = 0;
        Log4jUtil.info("zheshiyigeceshi");
        try {
            sum = 1 / 0;//这是错误代码
            sum=1/10000000;
        } catch (Exception e) {
            e.printStackTrace();
            Log4jUtil.logOutFormat(logger,e);
        }
    }
}
