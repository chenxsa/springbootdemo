package com.xgstudio.springbootdemo.api;

import com.xgstudio.springbootdemo.exception.AppError;
import com.xgstudio.springbootdemo.util.RestResultGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * controller的基类
 * Created by chenxsa on 2018-3-7.
 */
public abstract class BaseController {
    private Logger logger = LoggerFactory.getLogger(BaseController.class);
    /**
     * 处理异常函数
     * @param ex
     * @return org.springframework.http.ResponseEntity<com.digiwin.dap.middleware.cmc.AppError>
     */
    protected ResponseEntity<AppError> handleAppError(Exception ex){
        logger.error(ex.getMessage(),ex);
        return new ResponseEntity<AppError>(RestResultGenerator.genError(ex), HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /**
     * 处理异常函数
     * @param message
     * @return org.springframework.http.ResponseEntity<com.digiwin.dap.middleware.cmc.AppError>
     */
    protected ResponseEntity<AppError> handleAppError(String message){
        return new ResponseEntity<AppError>(RestResultGenerator.genError(message), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    /**
     * 处理异常函数
     * @param message
     * @return org.springframework.http.ResponseEntity<com.digiwin.dap.middleware.cmc.AppError>
     */
    protected ResponseEntity<AppError> handleAppError(String message,HttpStatus httpStatus){
        return new ResponseEntity<AppError>(RestResultGenerator.genError(message), httpStatus);
    }
    /**
     * 返回数据
     */
    protected ResponseEntity<?> handleResult(Object data){
        return ResponseEntity.ok(data);
    }
}
