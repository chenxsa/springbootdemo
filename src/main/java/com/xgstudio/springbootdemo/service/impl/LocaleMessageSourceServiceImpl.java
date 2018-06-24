package com.xgstudio.springbootdemo.service.impl;

import com.xgstudio.springbootdemo.service.ILocaleMessageSourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * 获取本地化消息的服务
 * @author chenxsa
 */
@Service
public class LocaleMessageSourceServiceImpl implements ILocaleMessageSourceService {

    @Autowired
    private MessageSource messageSource;

    /**
     * @param code ：对应messages配置的key.
     * @return 消息
     */
    @Override
    public String getMessage(String code){
        return getMessage(code, new  Object[]{});
    }

    /**
     *
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return 消息
     */
    @Override
    public String getMessage(String code,Object... args){
        return getMessage(code, args,"");
    }


    /**
     *
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return 消息
     */
    @Override
    public String getMessage(String code,Object[] args,String defaultMessage){
        //这里使用比较方便的方法，不依赖request.
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code, args, defaultMessage, locale);
    }
}
