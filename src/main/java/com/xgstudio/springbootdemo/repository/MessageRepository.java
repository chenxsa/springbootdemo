package com.xgstudio.springbootdemo.repository;

import com.xgstudio.springbootdemo.entity.Message;
import com.xgstudio.springbootdemo.entity.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 消息Repository
 * @author chenxsa
 */
@Repository
@Transactional(readOnly = true,rollbackFor = Exception.class)
public interface MessageRepository extends JpaRepository<Message,Long>, JpaSpecificationExecutor<Message> {

    /**
     * 根据消息Id查询消息日志
     * @param sendUserId
     * @return
     */
    List<Message> findBySendUserId(long sendUserId);

    /**
     * 根据消息Id更新消息状态
     * @param messageStatus
     * @param messageId
     * @return
     */
    @Modifying
    @Transactional(rollbackFor = Exception.class)
    @Query(value = "update Message m set m.status=:messageStatus where m.id= :messageId")
    int updateStatusByMessageId(@Param("messageStatus") MessageStatus messageStatus, @Param("messageId") long messageId) ;


}
