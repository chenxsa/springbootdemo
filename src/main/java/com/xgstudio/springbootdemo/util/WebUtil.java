package com.xgstudio.springbootdemo.util;


import com.xgstudio.springbootdemo.config.WebMvcConfigurationExtendConfig;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author chenxsa
 * @date: 2018-6-7 14:23
 * @Description:
 */
public class WebUtil {
    /**
     * @param response
     * @param data
     */
    public static void writeHttpResponse(HttpServletResponse response, Object data)  throws IOException{
        if (data == null) {
            return;
        }
         //将实体对象转换为JSON Object转换
        String json = WebMvcConfigurationExtendConfig.createObjectMapper().writeValueAsString(data);
        writeHttpResponse(response,json);
    }

    /**
     * @param response
     * @param data
     */
    public static void writeHttpResponse(HttpServletResponse response, String data)  throws IOException{
        if (data == null) {
            return;
        }
        PrintWriter out = null;
        try {
            //将实体对象转换为JSON Object转换
            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");
            out = response.getWriter();
            out.write(data);
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }
}
