package http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author: dxx
 * @version: V-Cloude
 * @date: 2018/12/9 14:46
 * @copyright©: 2018东方微银科技（北京）有限公司
 * @file_name: wybigdata
 * @pachage_name: http
 * @description:
 */
public class httpcliUtils {
    public static String sendPost(String url, String param,String charset) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        HttpURLConnection conn = null;
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection connx = realUrl.openConnection();
            conn = (HttpURLConnection)connx;
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(60);
            conn.setReadTimeout(60);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try{
                if(out!=null){
                    out.close();
                    System.out.println("close out");
                }
                if(in!=null){
                    System.out.println("close in");
                    in.close();
                }
                conn.disconnect();
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
}