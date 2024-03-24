package com.devspark.chatservice.repository;

import com.devspark.chatservice.entity.ChatRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChatRepository extends MongoRepository<ChatRecord, String> {
    List<ChatRecord> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
    List<ChatRecord> findByReceiverIdAndSenderId(Long receiverId, Long senderId);

    List<ChatRecord> findAllBySenderId(Long senderId);
}
