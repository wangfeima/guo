package http;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/11 14:58
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: com.dfwy.online.sparkstreamingtask.http
 * @description:此程序模拟了http service  可以设置端口和响应数据
 */
public class Server {
    private static final String BLANK = " ";
    private static final String CRLF = "\r\n";
    private int length = 0;
    private ServerSocket server;

    public static void main(String[] args) {
        new Server().start();
    }

    public void start() {
        try {
            server = new ServerSocket(8888);
            response();
        } catch (BindException e) {
            System.out.println("端口已被占用");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void response() {
        try {
            Socket client = server.accept();
            //响应
            StringBuilder responseContext = new StringBuilder();
            /*responseContext.append("<html><head><title>HTTP响应示例</title>" +
                    "</head><body>郝俊杰</body></html>");*/
            responseContext.append("{董a1:a2,董a3:a4}");
            StringBuilder response = new StringBuilder();
            //1)  HTTP协议版本、状态代码、描述
            response.append("HTTP/1.1").append(BLANK).append("200").append(BLANK).append("OK").append(CRLF);
            //2)  响应头(Response Head)
            response.append("Server:hjj Server/0.0.1").append(CRLF);
            response.append("Date:").append(new Date()).append(CRLF);
            response.append("Content-type:text/html;charset=GBK").append(CRLF);
            //正文长度 ：字节长度
            response.append("Content-Length:").append(responseContext.toString().getBytes().length).append(CRLF);
            //3)正文之前
            response.append(CRLF);
            //4)正文
            response.append(responseContext);
            System.out.println(responseContext);
            //输出流
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
            bw.write(response.toString());
            bw.flush();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}