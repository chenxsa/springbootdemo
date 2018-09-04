package com.xgstudio.springbootdemo.exception;

import org.springframework.http.HttpStatus;

import java.util.HashMap;

/**
 * 错误信息对象
 * Created by chenzwd on 2018/2/23.
 */

public class AppError {

    public AppError() {
    }
    /**
     * 构造函数
     *@param code 错误编号
     *@param message 错误消息
     *@返回值
     */
    public AppError(HttpStatus code, String message){
        this.code=code;
        this.message=message;
    }
    /**
     * 构造函数
     *@param code 错误编号
     *@param message 错误消息
     *@param exceptionDeteail   捕获的异常详细信息
     *@返回值
     */
    public AppError(HttpStatus code, String message, String exceptionDeteail) {
        this.code = code;
        this.message = message;
        exceptionDetail = exceptionDeteail;
    }
    /**
     * 构造函数
     *@param code 错误编号
     *@param message 错误消息
     *@param ex   捕获的异常
     *@返回值
     */
    public AppError(HttpStatus code, String message, Exception ex) {
        this.code = code;
        this.message = message;
        setExceptionDetail(ex);
    }

    /**
     * 构造函数
     * @param ex
     */
    public AppError(Exception ex) {
        this.code = HttpStatus.INTERNAL_SERVER_ERROR;
        this.message = ex.getMessage();
        setExceptionDetail(ex);
    }

    private HttpStatus code;
    private String message;
    private String exceptionDetail;
    private HashMap data;

    public HashMap getData() {
        return data;
    }

    public void setData(HashMap data) {
        this.data = data;
    }

    public HttpStatus getCode() {
        return code;
    }

    public void setCode(HttpStatus code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExceptionDetial() {
       return exceptionDetail;
    }
    static String getExceptionDetail(Exception e) {
        if (e==null){
            return  "";
        }
        StringBuffer msg = new StringBuffer("null");
        if (e != null) {
            msg = new StringBuffer("");
            String message = e.toString();
            int length = e.getStackTrace().length;
            if (length > 0) {
                msg.append(message + System.getProperty("line.separator"));
                for (int i = 0; i < length; i++) {
                    msg.append( e.getStackTrace()[i] + System.getProperty("line.separator"));
                }
            } else {
                msg.append(message);
            }
        }
        return  msg.toString().trim();
    }

    /**
     *
     * @param e
     */
    public void setExceptionDetail(Exception e) {

        this.exceptionDetail=getExceptionDetail(e);
    }
}