package com.xgstudio.springbootdemo.service;

import com.xgstudio.springbootdemo.SpringbootdemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
@WebAppConfiguration
public class ISendMessageServiceTests {

    @Test
    public void sendTest() {
        sendMessageService.sendMessage("hell,spring boot");
    }

    @Autowired
    ISendMessageService sendMessageService;

}
