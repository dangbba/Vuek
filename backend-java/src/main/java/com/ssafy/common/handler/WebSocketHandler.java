package com.ssafy.common.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.service.ChatService;
import com.ssafy.db.dto.ChatMessageDto;
import com.ssafy.db.dto.ChatRoomDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {
    private final ChatService chatService;
    private final ObjectMapper objectMapper;

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        log.info("메세지 전송 = {} : {}",session ,message.getPayload());
        String msg = message.getPayload();
        ChatMessageDto chatMessage = objectMapper.readValue(msg,ChatMessageDto.class);
        ChatRoomDto chatRoom = chatService.getChatRoomById(chatMessage.getConferenceId());
        chatRoom.handleMessage(session,chatMessage,objectMapper);

    }

}