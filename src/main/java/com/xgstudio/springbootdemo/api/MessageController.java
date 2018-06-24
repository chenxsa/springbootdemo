package com.xgstudio.springbootdemo.api;

import com.xgstudio.springbootdemo.entity.Message;
import com.xgstudio.springbootdemo.entity.MessageStatus;
import com.xgstudio.springbootdemo.repository.MessageRepository;
import com.xgstudio.springbootdemo.service.IMessageService;
import com.xgstudio.springbootdemo.service.ISendMessageService;
import com.xgstudio.springbootdemo.util.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 *
 * 消息发送Controller
 * @author chenxsa
 */
@RestController
@RequestMapping("/api/demo/v1")
public class MessageController extends BaseController {

    @Autowired
    private ISendMessageService sendMessageService;

    @Autowired
    private IMessageService messageService;


    /**
     * 发送消息
     * @param message xia
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @PostMapping(value = "/messages/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody Message message)   {
        boolean result=  sendMessageService.send(message);
        if (result) {
            message.setStatus(MessageStatus.SEND);
            long id=  messageService.saveMessage(message);
            return ResponseEntity.ok(MapUtils.create(new String[]{"sid", "msg"}, new Object[]{id, "发送成功"}) );
        }
        else {
            return handleAppError("发送失败");
        }
    }


    /**
     * 删除消息日志
     * @param sid
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    @DeleteMapping(value = "/messages/{sid}")
    public ResponseEntity<?> deleteMessageLog(@PathVariable("sid") Long sid) {
        messageService.deleteMessage(sid);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
