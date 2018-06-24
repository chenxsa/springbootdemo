package com.xgstudio.springbootdemo.api;

import com.xgstudio.springbootdemo.entity.Message;
import com.xgstudio.springbootdemo.service.ISendMessageService;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MessageController extends BaseController {

    @Autowired
    private ISendMessageService sendMessageService;

    /**
     * 发送消息
     * @param message xia
     * @return
     */
    @Transactional(readOnly = true,rollbackFor = Exception.class)
    @PostMapping(value = "/message/send")
    public ResponseEntity<?> sendMessage(@Valid @RequestBody Message message)   {
        boolean result=  sendMessageService.send(message);
        if (result) {
            return ResponseEntity.ok(result);
        }
        else {
            return handleAppError("发送失败");
        }
    }
}
