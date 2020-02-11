package jdbc;

import java.sql.*;

public class mysqltest {

    public static void main(String[] args) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        // MySQL的JDBC连接语句
        // URL编写格式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
        String url = "jdbc:mysql://localhost:3306/test?user=root&password=123?useSSL=true&serverTimezone=UTC";
        // 数据库执行的语句
        String sql = "insert into b values(1,'zhangsan',21);";//插入一条记录
        //String sql = "create table stuinfo(id char(12),name char(20),age int);";//创建一个表
        // 查询语句
        String cmd = "select * from b;";
        try {
            //Class.forName("com.mysql.cj.jdbc.Driver");
            Class.forName("com.mysql.jdbc.Driver"); // 加载驱
            conn = DriverManager.getConnection(url); // 获取数据库连
            stmt = conn.createStatement();// 创建执行环境
            stmt.execute(sql);// 执行SQL语
            // 读取数
            rs = stmt.executeQuery(cmd);// 执行查询语句，返回结果数据
            rs.last(); // 将光标移到结果数据集的最后一行，用来下面查询共有多少行记
            System.out.println("共有" + rs.getRow() + "行记录：");// 将光标移到结果数据集的开
            rs.beforeFirst();// 将光标移到结果数据集的开头
            while (rs.next()) {// 循环读取结果数据集中的所有记录
                System.out.println(rs.getRow() + "、 学号:" + rs.getString("id")
                        + "\t姓名:" + rs.getString("username") + "\t年龄:"
                        + rs.getInt("scores"));
            }
            try {
                if (rs != null)
                    rs.close(); // 关闭结果数据集
                if (stmt != null)
                    stmt.close(); // 关闭执行环境
                if (conn != null)
                    conn.close(); // 关闭数据库连接
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
