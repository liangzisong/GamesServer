package com.mangni.vegaplan.wzh.Http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import com.mangni.vegaplan.toolshelper.Bean;
import com.mangni.vegaplan.toolshelper.EncryptUtil;
public class HttpRequest extends Thread{
    /**
     * 向指定URL发送GET方法的请求
     * 
     * @param url
     *            发送请求的URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = connection.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }
    /*
    public void run() {  
    	String qwe="13812344321&123456";
        //String qwe = "123456&test1&3&id,username,pwd";
    	for(int i=0;i<10;i++)
    	{
	        String sr = null;
			try {
				sr = HttpRequest.sendPost("http://192.168.1.117:8010/login", URLEncoder.encode(EncryptUtil.aesEncrypt(qwe),"utf-8"));
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        //String sr=HttpRequest.sendPost("http://192.168.1.157:8010/getplayerdata/", EncryptUtil.aesEncrypt(qwe));
	        try {
				System.out.println(i+EncryptUtil.aesDecrypt(sr));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	}
    } 
    */
    public static void main(String[] args) throws Exception {
        //发送 GET 请求
//    	String asd = "aaa="+URLEncoder.encode("a20%a a+a","utf-8");
//        String s=HttpRequest.sendGet("http://192.168.1.157:8009/hello", asd);
//        System.out.println(s)
        
    	//发送 POST 请求
    	new Bean();
    	String qwe="16&chip&single$93&2015/09/12 16:32:21:135";
    	//String str=URLEncoder.encode(EncryptUtil.aesEncrypt(qwe),"utf-8");
    	//String qwe = "16&test1&3&id,username,pwd";
    	String sr=HttpRequest.sendPost("http://jzhehe.eicp.net:8010/get/","a=sd");
    	//String sr=HttpRequest.sendPost("ws://jzhehe.eicp.net:8009//asd//", "123123");
    	//String sr=HttpRequest.sendPost("http://192.168.1.157:8009/getplayerdata/", EncryptUtil.aesEncrypt(qwe));
    	
    	System.out.println(EncryptUtil.aesDecrypt(sr));
    	
    	/*
    	for(int i=0;i<1000;i++)
    	{
    		HttpRequest a = new HttpRequest(); 
    		a.start();
    	}
    	*/
    }
}