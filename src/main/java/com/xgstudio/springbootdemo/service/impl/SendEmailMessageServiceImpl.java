package com.xgstudio.springbootdemo.service.impl;

import com.xgstudio.springbootdemo.config.EmailProperties;
import com.xgstudio.springbootdemo.entity.Message;
import com.xgstudio.springbootdemo.entity.MessageStatus;
import com.xgstudio.springbootdemo.service.IMessageService;
import com.xgstudio.springbootdemo.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 发送邮件消息
 * @author chenxsa
 */
@Service
public class SendEmailMessageServiceImpl  implements ISendMessageService {
    @Autowired
    EmailProperties emailProperties;

    /**
     * 发送消息
     * @param msg 消息体
     */
    @Override
    public boolean send(Message msg){
        //todo:发送消息

        return true;
    }
}
