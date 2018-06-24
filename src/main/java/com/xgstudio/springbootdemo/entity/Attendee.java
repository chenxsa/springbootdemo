package com.xgstudio.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 消息参与者
 * @author chenxsa
 */
@Entity
@Table(name = "demo_message_attendee")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Attendee extends ManagerEntity {

    @JsonBackReference
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "message_id",nullable = false,columnDefinition="BIGINT DEFAULT 0")
    private Message message;
    @Column(name = "user_name",nullable = false,columnDefinition="VARCHAR(100) DEFAULT ''")
    private String userName="";
    @Column(name = "user_email",nullable = false,columnDefinition="VARCHAR(100) DEFAULT ''")
    @NotNull(message = "用户Email不能为空")
    private String userEmail="";


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
