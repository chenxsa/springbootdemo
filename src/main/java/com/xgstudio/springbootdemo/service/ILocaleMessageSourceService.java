package com.xgstudio.springbootdemo.service;

/**
 * 获取本地化消息的服务
 * @author  chenxsa on 2018-3-6.
 */
public interface ILocaleMessageSourceService {
    /**
     * 获取消息
     * @param code ：对应messages配置的key.
     * @return
     */
    String getMessage(String code);
    /**
     *获取消息
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @return
     */
    String getMessage(String code, Object... args);
    /**
     *获取消息
     * @param code ：对应messages配置的key.
     * @param args : 数组参数.
     * @param defaultMessage : 没有设置key的时候的默认值.
     * @return
     */
    String getMessage(String code, Object[] args, String defaultMessage);
}
