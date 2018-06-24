package com.xgstudio.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.xgstudio.springbootdemo.validator.MessageValid;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * 消息
 * @author chenxsa
 */
@Entity
@Table(name = "demo_message_log")
@MessageValid
public class Message extends ManagerEntity {

    @Column(name = "send_user_id")
    private long sendUserId;
    @Column(name = "context",nullable=false,columnDefinition="VARCHAR(20000)")
    @NotNull(message = "{MESSAGE.ERROR.E00002}")
    @Size(min = 1)
    private String context;
    @Column(name = "status",nullable=false)
    private MessageStatus status;

    @JsonManagedReference
    @OneToMany(cascade=CascadeType.ALL ,fetch=FetchType.LAZY )
    private List<OperationLog> operationLogs=new ArrayList<OperationLog>();

    public long getSendUserId() {
        return sendUserId;
    }

    public void setSendUserId(long sendUserId) {
        this.sendUserId = sendUserId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public List<OperationLog> getOperationLogs() {
        return operationLogs;
    }

    public void setOperationLogs(List<OperationLog> operationLogs) {
        this.operationLogs = operationLogs;
    }
}
