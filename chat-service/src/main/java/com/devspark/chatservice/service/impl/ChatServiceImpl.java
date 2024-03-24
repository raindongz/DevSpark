package com.devspark.chatservice.service.impl;

import com.devspark.chatservice.constants.ChatRecordTypeConstants;
import com.devspark.chatservice.entity.ChatRecord;
import com.devspark.chatservice.exception.customExceptions.SaveRecordException;
import com.devspark.chatservice.pojo.dto.GetChatHistoryDTO;
import com.devspark.chatservice.pojo.dto.SendMessageDTO;
import com.devspark.chatservice.pojo.vo.GetChatHistoryVO;
import com.devspark.chatservice.pojo.vo.SendMessageVO;
import com.devspark.chatservice.repository.ChatRepository;
import com.devspark.chatservice.service.ChatService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;

    @Override
    public GetChatHistoryVO getChatHistory(Long userId, GetChatHistoryDTO getChatHistoryDTO) {
        List<ChatRecord> allByReceiverIdAndSenderId = chatRepository.findByReceiverIdAndSenderId(userId, getChatHistoryDTO.matchedUserId());
        List<ChatRecord> allBySenderIdAndReceiverId = chatRepository.findBySenderIdAndReceiverId(userId, getChatHistoryDTO.matchedUserId());

        List<ChatRecord> mergedRecords = new ArrayList<>(allByReceiverIdAndSenderId);
        mergedRecords.addAll(allBySenderIdAndReceiverId);

        List<ChatRecord> resultList = mergedRecords.stream().sorted(Comparator.comparing(ChatRecord::getCreateTime)).toList();
        return new GetChatHistoryVO(resultList);
    }

    @Override
    public SendMessageVO sendMessage(Long userId, SendMessageDTO sendMessageDTO) {
        ChatRecord chatRecord = new ChatRecord();
        chatRecord.setSenderId(userId);
        chatRecord.setReceiverId(sendMessageDTO.receiverId());
        chatRecord.setContent(sendMessageDTO.message());
        chatRecord.setType(ChatRecordTypeConstants.TYPE_TEXT);
        chatRecord.setCreateTime(new Date());
        try {
            chatRepository.save(chatRecord);
        }catch (Exception e){
            log.error("save record error, record: " + chatRecord.toString() + " error: " + e);
            throw new SaveRecordException("send message failed");
        }
        return new SendMessageVO(true);
    }
}
