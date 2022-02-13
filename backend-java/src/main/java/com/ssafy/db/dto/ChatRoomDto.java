package com.ssafy.db.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.checkerframework.checker.units.qual.C;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import javax.persistence.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Data
public class ChatRoomDto {

    private long conferenceId;
    @ElementCollection(fetch = FetchType.LAZY)
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDto create(long idconference){
        ChatRoomDto room = new ChatRoomDto();
        room.conferenceId = idconference;
        return room;
    }

    public void handleMessage(WebSocketSession session, ChatMessageDto chatMessage,
                              ObjectMapper objectMapper) throws IOException {
        if(chatMessage.getType() == ChatMessageDto.MessageType.ENTER){
            sessions.add(session);
            chatMessage.setContent(chatMessage.getSender() + "님이 입장하셨습니다.");
        }
        else if(chatMessage.getType() == ChatMessageDto.MessageType.LEAVE){
            sessions.remove(session);
            chatMessage.setContent(chatMessage.getSender() + "님임 퇴장하셨습니다.");
        }
        else{
            chatMessage.setContent(chatMessage.getSender() + " : " + chatMessage.getContent());
        }
        send(chatMessage,objectMapper);
    }

    private void send(ChatMessageDto chatMessage, ObjectMapper objectMapper) throws IOException {
        TextMessage textMessage = new TextMessage(objectMapper.
                writeValueAsString(chatMessage.getContent()));
        for(WebSocketSession sess : sessions){
            sess.sendMessage(textMessage);
        }
    }
}