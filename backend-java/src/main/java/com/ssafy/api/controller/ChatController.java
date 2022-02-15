package com.ssafy.api.controller;

import com.ssafy.api.service.ChatService;
import com.ssafy.api.service.ChatServiceImpl;
import com.ssafy.db.dto.ChatMessageDto;
import com.ssafy.db.dto.ChatRoomDto;
import com.ssafy.db.repository.ChatRoomRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@Controller
//public class ChatController {
//
//	@MessageMapping("/receive")
//	@SendTo("/send")
//
//	public SocketVO SocketHandler(SocketVO socketVO) {
//		String userName = socketVO.getUserName();
//		String content = socketVO.getContent();
//
//		SocketVO result = new SocketVO(userName, content);
//		return result;
//	}
//
//}
//@CrossOrigin(originPatterns = {"*"})
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/api/v1/chat")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@MessageMapping("/chat.register")
	@SendTo("/topic/public")
	public ChatMessageDto register(@Payload ChatMessageDto chatMessageDto, SimpMessageHeaderAccessor headerAccessor) {
		return chatService.register(chatMessageDto, headerAccessor);
	}

	@MessageMapping("/chat.send")
	@SendTo("/topic/public")
	public ChatMessageDto sendMessage(@Payload ChatMessageDto chatMessageDto) {
		return chatMessageDto;
	}

 	@MessageMapping("/chat.leave")
	public void leaveRoom(@Payload ChatMessageDto chatMessageDto) {
		chatService.leaveRoom(chatMessageDto);
	}

	@GetMapping("/topic/{idconference}")
	public ChatRoomDto getChatRoomById(@PathVariable long idconference){
		return chatService.getChatRoomById(idconference);
	}

	@PostMapping("/topic/create")
	@ResponseBody
	public long createChatRoom(@RequestParam long idconference){
		return chatService.createChatRoom(idconference);
	}


}
