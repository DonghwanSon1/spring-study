package com.kh.ws.controller;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
 * 접속한 사용자를 기억해두고 모두에 대한 처리를 수행하는 서버
 * 
 * 
 */
public class WebSocketGroupServer extends TextWebSocketHandler{

	/*
	 * 사용자를 기억하기 위한 저장소
	 * - 중복 불가
	 * - 동기화 지원
	 * 
	 */
	
	private Set<WebSocketSession> users = new CopyOnWriteArraySet<WebSocketSession>();
	
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		users.add(session);
		System.out.println("사용자 접속 : 현재 : " + users.size() + "명");
	
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		TextMessage newMessage = new TextMessage(message.getPayload());
		//session.sendMessage(message);
		
		for(WebSocketSession ws : users) {
			ws.sendMessage(newMessage);
		}
	
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		users.remove(session);
		System.out.println("사용자 종료 ! 현재 : " + users.size() + "명");
	
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
