package com.xgstudio.springbootdemo.util;

import javax.net.ssl.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * 实现GET以及POST请求
 */
public class UrlReqUtil {

    /**
     * 根据url获取json数据
     * @param url
     * @return
     * @throws Exception
     */
    public static String get(String url) throws  Exception{
        HttpURLConnection http = null;
        InputStream is = null;
        try {
            http=createConnection(url);
            http.setRequestMethod("GET");
            http.setRequestProperty("Charset", "UTF-8");
            http.setDoOutput(true);
            http.setDoInput(true);
            System.setProperty("sun.net.client.defaultConnectTimeout", "30000");
            System.setProperty("sun.net.client.defaultReadTimeout", "30000");
            http.connect();
            is =http.getInputStream();
            int size =is.available();
            byte[] jsonBytes =new byte[size];
            is.read(jsonBytes);
            return   new String(jsonBytes,"UTF-8");
        } catch (Exception e) {
            throw e;
        }finally {
            try {
                if(null != http) http.disconnect();
                if (null != is) is.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 根据url来创建不同的connction
     * @param url
     * @return
     * @throws Exception
     */
    static  HttpURLConnection createConnection(String url) throws Exception{
        HttpURLConnection http = null;
        URL urlGet = new URL(url);
        if (url.startsWith("https") || url.startsWith("HTTPS")){
            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public void checkServerTrusted(X509Certificate[] chain,
                                               String authType) throws CertificateException {
                }
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            sslContext.init(null, new TrustManager[]{tm},  new java.security.SecureRandom());

            // 从上述SSLContext对象中得到SSLSocketFactory对象
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            http = (HttpsURLConnection) urlGet.openConnection();
            ((HttpsURLConnection)http).setSSLSocketFactory(ssf);
        }else {
            http = (HttpURLConnection) urlGet.openConnection();
        }
        return  http;
    }

    /**
     * 上传文件到微信
     * @param url
     * @param fileBytes
     * @param fileName
     * @return
     * @throws Exception
     */
    public static String postFile(String url, byte[] fileBytes,String fileName) throws  Exception{
        HttpURLConnection http = null;
        OutputStream outputStream = null;
        BufferedReader reader = null;
        try {
            //创建连接
            http=createConnection(url);
            http.setRequestMethod("POST");
            //1.1输入输出设置
            http.setDoInput(true);
            http.setDoOutput(true);
            http.setUseCaches(false); // post方式不能使用缓存
            //1.2设置请求头信息
            http.setRequestProperty("Connection", "Keep-Alive");
            http.setRequestProperty("Charset", "UTF-8");
            //1.3设置边界
            String BOUNDARY = "----------" + System.currentTimeMillis();
            http.setRequestProperty("Content-Type","multipart/form-data; boundary="+ BOUNDARY);


            // 请求正文信息
            // 第一部分：
            //2.将文件头输出到微信服务器
            StringBuilder sb = new StringBuilder();
            sb.append("--"); // 必须多两道线
            sb.append(BOUNDARY);
            sb.append("\r\n");
            sb.append("Content-Disposition: form-data;name=\"media\";filelength=\"" + fileBytes.length
                    + "\";filename=\""+ fileName + "\"\r\n");
            sb.append("Content-Type:application/octet-stream\r\n\r\n");
            byte[] head = sb.toString().getBytes("utf-8");
            // 获得输出流
            outputStream = new DataOutputStream(http.getOutputStream());
            // 将表头写入输出流中：输出表头
            outputStream.write(head);

            //3.将文件正文部分输出到微信服务器
            // 把文件以流文件的方式 写入到微信服务器中
            outputStream.write(fileBytes);
            //4.将结尾部分输出到微信服务器
            byte[] foot = ("\r\n--" + BOUNDARY + "--\r\n").getBytes("utf-8");// 定义最后数据分隔线
            outputStream.write(foot);
            outputStream.flush();
            outputStream.close();
            outputStream = null;
            //5.将微信服务器返回的输入流转换成字符串
            InputStream inputStream = http.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            reader = new BufferedReader(inputStreamReader);

            String str = null;
            StringBuffer buffer = new StringBuffer();
            while ((str = reader.readLine()) != null) {
                buffer.append(str);
            }

            reader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            http.disconnect();
            return buffer.toString();
        } catch (Exception e) {
            throw  e;
        }finally {
            try{
                if(null != http) http.disconnect();
                if(null != outputStream) outputStream.close();
                if(null != reader) reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

    }

    /**
     * post json 数据到指定的url
     * @param url 地址
     * @param data json数据
     * @return
     * @throws Exception
     */
    public static String post(String url,String data) throws  Exception{
        HttpURLConnection http = null;
        PrintWriter out = null;
        BufferedReader reader = null;
        try {
            //创建连接
            http=createConnection(url);
            http.setDoOutput(true);
            http.setDoInput(true);
            http.setRequestMethod("POST");
            http.setUseCaches(false);
            http.setInstanceFollowRedirects(true);
            http.setRequestProperty("Content-Type", "application/json");
            http.setRequestProperty("connection", "keep-alive");
            http.connect();
            //POST请求
            OutputStreamWriter outWriter = new OutputStreamWriter(http.getOutputStream(), "utf-8");
            out = new PrintWriter(outWriter);
            out.print(data);
            out.flush();
            out.close();
            out = null;

            //读取响应
            reader = new BufferedReader(new InputStreamReader(
                    http.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            reader = null;
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw  e;
        }finally {
            try{
                if(null != http) http.disconnect();
                if(null != out) out.close();
                if(null != reader) reader.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}