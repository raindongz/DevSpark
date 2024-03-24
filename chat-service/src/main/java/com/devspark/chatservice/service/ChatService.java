package com.devspark.chatservice.service;

import com.devspark.chatservice.pojo.dto.GetChatHistoryDTO;
import com.devspark.chatservice.pojo.dto.SendMessageDTO;
import com.devspark.chatservice.pojo.vo.GetChatHistoryVO;
import com.devspark.chatservice.pojo.vo.SendMessageVO;

public interface ChatService {

    GetChatHistoryVO getChatHistory(Long userId, GetChatHistoryDTO getChatHistoryDTO);
    SendMessageVO sendMessage(Long userId, SendMessageDTO sendMessageDTO);
}
