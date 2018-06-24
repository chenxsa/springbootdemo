package com.xgstudio.springbootdemo.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xgstudio.springbootdemo.SpringbootdemoApplication;
import com.xgstudio.springbootdemo.entity.Attendee;
import com.xgstudio.springbootdemo.entity.Message;
import com.xgstudio.springbootdemo.entity.MessageStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author chenxsa
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringbootdemoApplication.class)
@WebAppConfiguration
public class MessageControllerTests {
    //注入WebApplicationContext
    @Autowired
    private WebApplicationContext webContext;

    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setupMockMvc() {
        //设置MockMvc
         mockMvc = MockMvcBuilders
                .webAppContextSetup(webContext)
               // .addFilter(jwtAuthenticationFilter,"/*")
                .build();
    }
    @Transactional
    @Rollback(true)
    @Test
    public void sendTest() throws Exception {
        Message message=new Message();
        message.setContext("test message");
        message.setStatus(MessageStatus.SEND);
        Attendee attendee=new Attendee();
        attendee.setMessage(message);
        attendee.setUserEmail("test@qq.com");
        attendee.setUserName("test");
        message.getAttendees().add(attendee);
        String json= objectMapper.writeValueAsString(message);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders
                .post("/api/demo/v1/messages/send")
                .header("auth-user","demo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.sid").exists())
                .andExpect(jsonPath("$.msg").value("发送成功"))
                .andReturn();
        json=  mvcResult.getResponse().getContentAsString();
         Map<String, String> result=  objectMapper.readValue(json, new TypeReference<HashMap<String, String>>() { });
        String sid =  result.get("sid");
        mockMvc.perform(MockMvcRequestBuilders
                .delete("/api/demo/v1/messages/"+sid)
                .header("auth-user","demo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}
