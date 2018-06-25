package com.xgstudio.springbootdemo.util;

import com.xgstudio.springbootdemo.exception.AppError;
import org.springframework.http.HttpStatus;

/**
 * @author chenxsa
 * @date: 2018-6-8 13:39
 * @Description:
 */
public class RestResultGenerator {

    /**
     *
     * @param message
     * @return
     */
    public static AppError genError(String message , Exception e){
        AppError result = new AppError ();
        result.setMessage(message);
        result.setExceptionDetail( e);
        return result;
    }
    /**
     *
     * @param e
     * @param <T>
     * @return
     */
    public static <T> AppError genError(Exception e){
        AppError result = new AppError();
        result.setMessage(e.getMessage());
        result.setExceptionDetail(e);
        return result;
    }


    /**
     *
     * @param message
     * @param <T>
     * @return
     */
    public static <T> AppError genError( String message){
        AppError result = new AppError();
        result.setMessage(message);
        return result;
    }


    /**
     * 没有登陆异常
     * @param <T>
     * @return
     */
    public static <T> AppError genNoAuth(){
        AppError result = new AppError();
        result.setCode(HttpStatus.UNAUTHORIZED);
        result.setMessage("没有权限访问或者授权已过期，请登陆，并设置用户Token,如果是访问资源，请设置访问资源所需要的Token");
        return result;
    }

    /**
     *
     * @param <T>
     * @return
     */
    public static <T> AppError genNoAuthAccess(){
        AppError result = new AppError();
        result.setMessage("没有权限访问或者授权已过期，请重新根据当前已经登陆的用户获取访问资源的权限Token");
        return result;
    }
}
