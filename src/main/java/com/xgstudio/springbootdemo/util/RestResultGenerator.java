package com.xgstudio.springbootdemo.util;

import com.xgstudio.springbootdemo.api.model.ResponseResult;

/**
 * @author chenxsa
 * @date: 2018-6-8 13:39
 * @Description:
 */
public class RestResultGenerator {
    /**
     *
     * @param data
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genResult(T data, String message){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setData(data);
        result.setSuccess(true);
        result.setMessage(message);
        return result;
    }
    /**
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genResult(T data){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }
    /**
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genError( String message ,Exception e){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setSuccess(false);
        result.setMessage(message);
        result.setExceptionDetail(getExceptionDetail(e));
        return result;
    }
    /**
     *
     * @param e
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genError(Exception e){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setSuccess(false);
        result.setMessage(e.getMessage());
        result.setExceptionDetail(getExceptionDetail(e));
        return result;
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
     * @param message
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genError( String message){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setSuccess(false);
        result.setMessage(message);
        return result;
    }


    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genNoAuth(){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setSuccess(false);
        result.setMessage("没有权限访问或者授权已过期，请登陆，并设置用户Token,如果是访问资源，请设置访问资源所需要的Token");
        return result;
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> ResponseResult<T> genNoAuthAccess(){
        ResponseResult<T> result = new ResponseResult<T>();
        result.setSuccess(false);
        result.setMessage("没有权限访问或者授权已过期，请重新根据当前已经登陆的用户获取访问资源的权限Token");
        return result;
    }
}
