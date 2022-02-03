package com.ssafy.db.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@ToString
@Data
public class ChatRoomDto {
    private int Conferenceid;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public static ChatRoomDto create(String name){
        ChatRoomDto room = new ChatRoomDto();
        return room;
    }
}