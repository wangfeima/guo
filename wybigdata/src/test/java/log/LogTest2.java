package log;

import common.utils.log.Log4jUtil;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.slf4j.LoggerFactory;

public class LogTest2 {

    private static Logger logger = Logger.getLogger(LogTest2.class.getName());

    public static void main(String[] args) {
        //logger.info("logs类main方法：" + LogTest2.class.getName());
        int sum = 0;
        //Log4jUtil.debug("debug2信息");
        //Log4jUtil.info("info2信息");
        //Log4jUtil.warn("warn2信息");
        //Log4jUtil.error("error2信息");
        try {
            sum = 1 / 0;//这是错误代码
            sum=1/10000000;
        } catch (Exception e) {
            //logger.error("findPage: " + e);
            //logger.fatal("findPage: " + e);
            //Log4jUtil.error("error2信息-->" + e.getMessage());
            //Log4jUtil.error("error2信息------->", e);
            StackTraceElement[] st = e.getStackTrace();
            for (StackTraceElement stackTraceElement : st) {
                /**
                 * 错误信息：在类:[log.LogTest2]运行main时在第[20]行代码处发生异常!错误信息是：[/ by zero]
                 *----------------------------------------------------------------------
                 *2018-12-11 10:35:04 [Log4jUtil.java:68]-[ERROR]:在类 log.LogTest2 运行 main 时,第 20 行发生异常!异常信息：/ by zero
                 *2018-12-11 10:35:04 [LogTest2.java:38]-[ERROR]:在类 log.LogTest2 运行 main 时,第 20 行发生异常!异常信息：/ by zero
                 */
                String exclass = stackTraceElement.getClassName();
                String method = stackTraceElement.getMethodName();

                String errorMessage="在类 " + exclass + " 运行 " + method + " 时,第 " + stackTraceElement.getLineNumber()
                        + " 行发生异常!异常信息：" + e.getMessage();


                System.out.println("错误信息：在类:[" + exclass + "]运行"
                        + method + "时在第[" + stackTraceElement.getLineNumber()
                        + "]行代码处发生异常!错误信息是：[" + e.getMessage()+"]");
                System.out.println("----------------------------------------------------------------------");
                Log4jUtil.error(errorMessage);
                logger.error(errorMessage);
            }
        }
    }
}