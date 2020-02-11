package common.utils.jdbc;

import org.apache.commons.dbcp2.BasicDataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
/**
 * Description: 数据库连接池类
 * @author dinglq
 */
public class ConnectionPool {
    private static BasicDataSource bs = null;
    /**
     * 创建数据源
     * @return
     */
    public static BasicDataSource getDataSource() throws Exception {
        if (bs == null) {
            bs = new BasicDataSource();
            bs.setDriverClassName("com.mysql.jdbc.Driver");
            bs.setUrl("jdbc:mysql://localhost:3306/wybigdata?characterEncoding=utf8&useSSL=true&serverTimezone=UTC");
            bs.setUsername("root");
            bs.setPassword("123");
            //#最大活跃数
            bs.setMaxTotal(50);
            //#初试连接数
            bs.setInitialSize(10);
            //#最小idle数
            bs.setMinIdle(3);
            //#最大idle数
            bs.setMaxIdle(10);
            //#最长等待时间(毫秒)被转成了2S
            bs.setMaxWaitMillis(2 * 10000);
            //#一个被抛弃连接可以被移除的超时时间，单位为秒
            bs.setRemoveAbandonedTimeout(180);
            /*标记是否删除超过removeAbandonedTimout所指定时间的被遗弃的连接。
            如果设置为true，则一个连接在超过removeAbandonedTimeout所设定的时间未使用即被认为是应该被抛弃并应该被移除的。
            创建一个语句，预处理语句，可调用语句或使用它们其中的一个执行查询（使用执行方法中的某一个）会重新设置其父连接的lastUsed 属性。
            在写操作较少的应用程序中将该参数设置为true可以将数据库连接从连接关闭失败中恢复。*/
            bs.setRemoveAbandonedOnBorrow(true);
            bs.setRemoveAbandonedOnMaintenance(true);
            //指明在将对象归还给连接池前是否需要校验。
            bs.setTestOnReturn(true);
            //指明在从池中租借对象时是否要进行校验，如果对象校验失败，则对象将从池子释放，然后我们将尝试租借另一个
            bs.setTestOnBorrow(true);
        }
        return bs;
    }

    /**
     * 获取数据库连接
     * @return
     */

    public static Connection getConnection() {
        Connection con = null;
        try {
            if (bs != null) {
                con = bs.getConnection();
            } else {
                con = getDataSource().getConnection();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }
    /**
     * 释放数据源,最后不使用后关闭数据源
     */
    public static void shutDownDataSource() throws Exception {
        if (bs != null) {
            bs.close();
        }
    }
    /**
     * 关闭连接
     * @param ps
     * @param con
     * @param rs
     */
    public static void closeCon(PreparedStatement ps, Connection con, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch(Exception e){
                e.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 关闭连接
     * @param ps
     * @param con
     */
    public static void closeCon(PreparedStatement ps, Connection con) {
        if (ps != null) {
            try {
                ps.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

