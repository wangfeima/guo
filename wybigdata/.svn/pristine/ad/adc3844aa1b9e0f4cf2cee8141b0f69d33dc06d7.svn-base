package common.utils.log;
// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
// import org.apache.log4j.PropertyConfigurator;

/**
 * 记录日志的工具类 主要输出字符串 </br>
 * @version 1.0
 * @since
 * @author
 */
public class Log4jUtil {
    // log4j配置文件
    // private static final String PROPERTIES_FILE_NAME = "log4j.properties";
    private static Log4jUtil instance = null;
    private static Logger logger = null;
    // 利用JCL(commons-logging)组件
    // private static Log jclLogger = null;

    static {
        // PropertyConfigurator.configure(PROPERTIES_FILE_NAME);
        // logger = Logger.getRootLogger();
        // jclLogger = LogFactory.getLog(Log4jUtil.class); // 创建JCL的Log实例
        logger = Logger.getLogger(Log4jUtil.class);
    }

    private Log4jUtil() {
    }
    public static Log4jUtil getInstance() {
        synchronized (Log4jUtil.class) {
            if (instance == null) {
                instance = new Log4jUtil();
            }
        }
        return instance;
    }

    public static void debug(String str) {
        logger.debug(str);
        // jclLogger.debug(str);
    }

    public static void debug(String str, Exception e) {
        logger.debug(str, e);
    }

    public static void info(String str) {
        logger.info(str);
        // jclLogger.info(str);
    }

    public static void info(String str, Exception e) {
        logger.info(str, e);
    }

    public static void warn(String str) {
        logger.warn(str);
        // jclLogger.warn(str);
    }

    public static void warn(String str, Exception e) {
        logger.warn(str, e);
    }

    public static void error(String str) {
        logger.error(str);
        // jclLogger.error(str);
    }

    public static void error(String str, Exception e) {
        logger.error(str, e);
    }

    public static void fatal(String str) {
        logger.fatal(str);
        // jclLogger.fatal(str);
    }

    public static void fatal(String str, Exception e) {
        logger.fatal(str, e);
    }



    public static void logOutFormat(Logger logger,Exception e){
        StackTraceElement[] st = e.getStackTrace();
        for (StackTraceElement stackTraceElement : st) {
            String exclass = stackTraceElement.getClassName();
            String method = stackTraceElement.getMethodName();
            StringBuffer stringBuffer=new StringBuffer();
            String message = stringBuffer.append("在类 ").append(exclass).append(" 运行 ").append(method).append(" 时,第 ").append(stackTraceElement.getLineNumber())
                    .append(" 行发生异常!异常信息：").append(e.getMessage()).toString();
            /*String errorMessage="在类 " + exclass + " 运行 " + method + " 时,第 " + stackTraceElement.getLineNumber()
                    + " 行发生异常!异常信息：" + e.getMessage();*/
            logger.error(message);
        }
    }
}