package com.kh.ws.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebSocketController {

	
	@RequestMapping("/basic")
	public String basic() {
		return "websocket/basic";
	}
	
	@RequestMapping("/group")
	public String group() {
		return "websocket/group";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
