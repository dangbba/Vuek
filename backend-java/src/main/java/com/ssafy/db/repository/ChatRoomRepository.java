package com.ssafy.db.repository;

import com.ssafy.db.dto.ChatRoomDto;
import lombok.Getter;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class ChatRoomRepository {
    private Map<Long, ChatRoomDto> chatRoomMap;

    @PostConstruct
    private void init(){
        chatRoomMap = new LinkedHashMap<>();
    }

    public ChatRoomDto getChatRoomById(@PathVariable long idconference){
        return chatRoomMap.get(idconference);
    }

    public long createChatRoom(@RequestParam long idconference){
        ChatRoomDto chatRoomDto = ChatRoomDto.create(idconference);
        chatRoomMap.put(chatRoomDto.getConferenceId(), chatRoomDto);
        return chatRoomDto.getConferenceId();
    }

    public void leave(long idconference, String userId) {
        ChatRoomDto participantRoom = this.chatRoomMap.get(idconference);

        if (participantRoom == null) {
            return;
        }

        participantRoom.setSessions(null);

        if (participantRoom == null) {
            this.chatRoomMap.remove(idconference);
        }
    }

}
