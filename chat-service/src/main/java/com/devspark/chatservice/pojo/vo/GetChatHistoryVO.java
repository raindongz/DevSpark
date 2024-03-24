package com.devspark.chatservice.pojo.vo;

import com.devspark.chatservice.entity.ChatRecord;

import java.util.List;

public record GetChatHistoryVO(List<ChatRecord> chatRecordList) {
}
