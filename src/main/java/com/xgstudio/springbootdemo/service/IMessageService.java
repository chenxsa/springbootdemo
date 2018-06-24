package com.xgstudio.springbootdemo.service;

import com.xgstudio.springbootdemo.entity.Message;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chenxsa
 */
public interface IMessageService {

    /**
     * 保存消息
     * @param message
     */
    long saveMessage(Message message);

    /**
     * 删除消息
     * @param messageId 删除
     */
     void deleteMessage(long messageId);
}
