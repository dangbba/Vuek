package com.ssafy.backend.model;

import java.util.HashSet;
import java.util.Set;

import org.springframework.web.socket.WebSocketSession;

import lombok.Data;

@Data
public class ChatRoomDto {
	private int Conferenceid;
	private Set<WebSocketSession> sessions = new HashSet<>();
	
    public static ChatRoomDto create(String name){
        ChatRoomDto room = new ChatRoomDto();
        return room;
    }
}