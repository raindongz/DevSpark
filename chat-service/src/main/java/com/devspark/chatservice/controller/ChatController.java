package com.devspark.chatservice.controller;

import com.devspark.chatservice.pojo.dto.GetChatHistoryDTO;
import com.devspark.chatservice.pojo.dto.SendMessageDTO;
import com.devspark.chatservice.pojo.vo.GetChatHistoryVO;
import com.devspark.chatservice.pojo.vo.SendMessageVO;
import com.devspark.chatservice.service.ChatService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    private final ChatService chatService;

    @PostMapping("/get-chat-history")
    public ResponseEntity<GetChatHistoryVO> getChatHistory(@RequestHeader(name = "user_id")Long userId,
                                            @RequestBody @Valid GetChatHistoryDTO getChatHistoryDTO){
        GetChatHistoryVO chatHistoryVO = chatService.getChatHistory(userId, getChatHistoryDTO);
        return ResponseEntity.ok(chatHistoryVO);
    }


    @PostMapping("/send-message")
    public ResponseEntity<?> sendMessage(@RequestHeader(name = "user_id")Long userId,
                                         @RequestBody @Valid SendMessageDTO sendMessageDTO){
        SendMessageVO sendMessageVO = chatService.sendMessage(userId, sendMessageDTO);
        return ResponseEntity.ok(sendMessageVO);
    }
}
