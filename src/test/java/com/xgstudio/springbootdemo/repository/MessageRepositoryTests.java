package com.xgstudio.springbootdemo.repository;

import com.xgstudio.springbootdemo.SpringbootdemoApplication;
import com.xgstudio.springbootdemo.entity.Attendee;
import com.xgstudio.springbootdemo.entity.Message;
import com.xgstudio.springbootdemo.entity.MessageStatus;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;

/**
 * @author chenxsa
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
@WebAppConfiguration
public class MessageRepositoryTests {
    @Autowired
    MessageRepository messageJpa;

    @Transactional
    @Rollback(true)
    @Test
    public void insertTest(){
        Message message=new Message();
        try {
            message.setContext("test message");
            message.setStatus(MessageStatus.SEND);
            Attendee attendee=new Attendee();
            attendee.setMessage(message);
            attendee.setUserEmail("test@qq.com");
            attendee.setUserName("test");
            message.getAttendees().add(attendee);
            message=messageJpa.save(message);
            Assert.assertTrue(message.getId()>0);
            Assert.assertTrue(messageJpa.count()==1);
        }
       finally {
            messageJpa.delete(message);
        }
    }
    @Rule
    public ExpectedException thrown= ExpectedException.none();
    /**
     * 测试校验不过的情况
     */
    @Test
    public void insertValidTest() {
        thrown.expect(TransactionSystemException.class);
        Message message = new Message();
        message.setContext("demo");
        message.setStatus(MessageStatus.SEND);
        messageJpa.save(message);
    }

    @Transactional
    @Rollback(true)
    @Test
    public void updateTest() {
        Message message = new Message();
        message.setContext("test message");
        message.setStatus(MessageStatus.SEND);
        message = messageJpa.save(message);
        Assert.assertTrue(message.getId() > 0);
        Assert.assertTrue(messageJpa.count() == 1);
        message.setStatus(MessageStatus.SUCCESS);


        messageJpa.save(message);
        message = messageJpa.findById(message.getId()).get();
        Assert.assertTrue(message.getStatus() == MessageStatus.SUCCESS);
    }
}
