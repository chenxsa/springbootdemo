package com.xgstudio.springbootdemo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 操作日志
 * @author chenxsa
 */
@Entity
@Table(name = "demo_operation_log") 
public class OperationLog extends ManagerEntity {
    
    @Column(name = "operation_time")
    private LocalDateTime operationTime;
    @Column(name = "operation_place")
    private String operationPlace;
    @Column(name = "status")
    private MessageStatus status;
    @Column(name = "retry_count")
    private int retryCount;
    @Column(name = "operation_by")
    private String operationBy;

    @JsonBackReference
    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "message_sid",nullable = false,columnDefinition="BIGINT DEFAULT 0")
    private Message message;

    public LocalDateTime getOperationTime() {
        return operationTime;
    }

    public void setOperationTime(LocalDateTime operationTime) {
        this.operationTime = operationTime;
    }

    public String getOperationPlace() {
        return operationPlace;
    }

    public void setOperationPlace(String operationPlace) {
        this.operationPlace = operationPlace;
    }

    public MessageStatus getStatus() {
        return status;
    }

    public void setStatus(MessageStatus status) {
        this.status = status;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public String getOperationBy() {
        return operationBy;
    }

    public void setOperationBy(String operationBy) {
        this.operationBy = operationBy;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
