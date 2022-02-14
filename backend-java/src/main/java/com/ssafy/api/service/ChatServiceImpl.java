package com.ssafy.api.service;

import com.ssafy.db.dto.ChatMessageDto;
import com.ssafy.db.dto.ChatRoomDto;
import com.ssafy.db.repository.ChatRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.LinkedHashMap;

@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatRoomRepository chatRoomRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @MessageMapping("/chat.register")
    @SendTo("/topic/public")
    public ChatMessageDto register(@Payload ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor) {
        headerAccessor.getSessionAttributes().put("username", chatMessageDto.getSender());
        return chatMessageDto;
    }

    @Override
    @MessageMapping("/chat.send")
    @SendTo("/topic/public")
    public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessageDto) {
        return chatMessageDto;
    }

    @Override
    @MessageMapping("/chat.leave")
    public void leaveRoom(@Payload ChatMessageDto chatMessageDto) {

        chatRoomRepository.leave(chatMessageDto.getConferenceId(), chatMessageDto.getSender());
        simpMessagingTemplate.convertAndSend("/participants/room." + chatMessageDto.getConferenceId(), chatMessageDto.getConferenceId());
    }

    @Override
    @GetMapping("/room/{idconference}")
    public ChatRoomDto getChatRoomById(@PathVariable long idconference){
        return chatRoomRepository.getChatRoomById(idconference);
    }
    @Override
    @PostMapping
    @ResponseBody
    public long createChatRoom(@RequestParam long idconference){
        return chatRoomRepository.createChatRoom(idconference);
    }


    @Override
    public void leave(long idconference, String userId) {
        chatRoomRepository.leave(idconference, userId);
    }
}
