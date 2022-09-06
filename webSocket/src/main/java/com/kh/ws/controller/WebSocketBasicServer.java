package com.kh.ws.controller;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/*
 * 웹 소켓(Web Socket)
 * 
 * - Web에서 수행하는 Socket통신
 * - 기본적으로 Web은 비연결형 통신이라는 특징을 가지고 있다.
 * - Socket통신은 연결형 통신
 * 
 */

/*
 * 웹소켓의 기본적인 이해를 돕기 위해 만든 서버
 * - WebSocketServer를 만들기 위해서는 특정 클래스/인터페이스 상속 필요
 * 
 */

//public class WebSocketBasicServer implements WebSocketHandler {

// extends해서 소스에 있는 오버라이드를 불러왔다.
public class WebSocketBasicServer extends TextWebSocketHandler {

	/*
	 * - 접속 시 실행되는 메소드
	 * - session : 접속한 사용자의 웹소켓 정보 (HttpSession이 아님!!!!!!!!!!!)
	 * 
	 */
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("접속 : ");
		System.out.println("session : " + session);
		
		
		
	}

	/*
	 * - 메시지 수신 시 실행되는 메소드
	 * - session : 접속한 사용자의 웹소켓 정보(HttpSession아님!!!!!!!!!!!!)
	 * - message : 사용자가 전송한 메시지 정보
	 * 			- payload : 실제 보낸 내용
	 * 			- byteCount : 보낸 메시지 크기 (byte)
	 * 			- last : 메시지의 종료 여부
	 * 
	 * 
	 */
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		System.out.println("수신 : ");
		System.out.println("session : " + session);
		System.out.println("message : " + message);
		
	}

	
	/*
	 * - 사용자 접속 종료 시 실행되는 메서드
	 * - session : 접속종료한 사용자의 웹소켓 정보(HttpSession아님!!!!!!!!!)
	 * - status : 접속이 종료된 원인과 관련된 정보
	 * 
	 */
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		
		System.out.println("종료 : ");
		System.out.println("session : " + session);
		System.out.println("status : " + status);
		
		
		
		
		
		
		
		
		
		
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
