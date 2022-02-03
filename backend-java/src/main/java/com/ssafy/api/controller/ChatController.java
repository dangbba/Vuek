package com.ssafy.api.controller;

import com.ssafy.db.vo.SocketVO;
import io.swagger.annotations.Api;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {
	
	@MessageMapping("/receive")
	@SendTo("/send")
	
	public SocketVO SocketHandler(SocketVO socketVO) {
		String username = socketVO.getUsername();
		String content = socketVO.getContent();
		
		SocketVO result = new SocketVO(username, content);
		return result;
	}

}
