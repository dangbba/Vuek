package com.ssafy.api.service;

import com.ssafy.db.dto.ChatMessageDto;
import com.ssafy.db.dto.ChatRoomDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface ChatService {
    ChatMessageDto register(@Payload ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor);
    ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessageDto);
    void leaveRoom(@Payload ChatMessageDto chatMessageDto);
    ChatRoomDto getChatRoomById(@PathVariable long idconference);
    long createChatRoom(long idconference);
    void leave(@RequestParam long idconference, String userId);

}
