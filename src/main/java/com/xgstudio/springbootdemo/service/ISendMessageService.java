package com.xgstudio.springbootdemo.service;

import com.xgstudio.springbootdemo.entity.Message;

/**
 * 发送消息
 * @author chenxsa
 */
public interface ISendMessageService {
    /**
     * 发送消息
     * @param msg 消息体
     */
    boolean send(Message msg);
}
