package com.xgstudio.springbootdemo.repository;

import com.xgstudio.springbootdemo.entity.OperationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 消息操作查询
 * @author chenxsa
 */
public interface OperationLogRepository extends JpaRepository<OperationLog,Long>,
        JpaSpecificationExecutor<OperationLog> {

    /**
     * 根据消息Id查询操作日志
     * @param messageId
     * @return
     */
    List<OperationLog> findByMessageId(long messageId);
}
