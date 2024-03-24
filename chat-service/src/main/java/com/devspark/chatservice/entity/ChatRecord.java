package com.devspark.chatservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@Document(collection = "chat_history")
public class ChatRecord {
    @Id
    @JsonIgnore
    private String id;
    private Long senderId;
    private Long receiverId;
    private String content;
    private Integer type;
    private Date createTime;
}
