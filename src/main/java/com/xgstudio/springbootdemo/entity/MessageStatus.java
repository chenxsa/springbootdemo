package com.xgstudio.springbootdemo.entity;

/**
 * 消息状态
 * @author chenxsa
 */
public enum MessageStatus {
    /**
     * 消息处于发送状态
     */
    SEND,
    /**
     * 消息处于重试状态
     */
    RETRY,
    /**
     * 消息处于发送成功状态
     */
    SUCCESS
}
