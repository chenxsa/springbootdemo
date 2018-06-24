package com.xgstudio.springbootdemo.service.impl;

import com.xgstudio.springbootdemo.entity.Message;
import com.xgstudio.springbootdemo.entity.OperationLog;
import com.xgstudio.springbootdemo.repository.MessageRepository;
import com.xgstudio.springbootdemo.repository.OperationLogRepository;
import com.xgstudio.springbootdemo.service.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author chenxsa
 */
@Service
public class MessageServiceImpl implements IMessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    OperationLogRepository operationLogRepository;
    /**
     * 保存消息
     * @param message
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public long saveMessage(Message message){
      return  messageRepository.save(message).getId();
    }

    /**
     * 删除消息
     * @param messageId 删除
     */
    @Override
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    public void deleteMessage(long messageId){
        //
        messageRepository.deleteById(messageId);
        //删除操作记录
        operationLogRepository.findByMessageId(messageId );
    }
}
