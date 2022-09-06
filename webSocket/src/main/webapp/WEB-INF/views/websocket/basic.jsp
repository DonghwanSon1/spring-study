<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>웹소켓 페이지</title>
    <!-- jQuery 라이브러리 -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
</head>
<body>

	<h1>내놔</h1>
	<button onclick="connect();">접속</button>
	<button onclick="disconnect();">종료</button>
	
	<script>
		
		var socket; 
		
		// 웹소켓 접속 합수
		function connect(){
			var uri = "ws://localhost:8006/ws/sc";
			socket = new WebSocket(uri);
			
			// 연결이 성공했는지 아닌지 확인할 수 있도록 예약작업(콜백)을 설정
			socket.onopen = function(){
				console.log("서버와 연결되었습니다.");
			}
			socket.onclose = function(){
				console.log("서버와 연결이 종료되었습니다.");
			}
			socket.onerror = function(e){
				console.log("오타내지맙시다.");
			}
			socket.onmessage = function(e){
				console.log("메세지가 도착하였습니다.");
			}
		}
		
		
		// 웹소켓 종료 함수
		function disconnect(){
			socket.close();
		}
		
		
		// 메시지 전송함수 : 입력한 글자를 불러와서 서버에 전송
		function send(){
			
			var text = $("#chat-input").val();
			if(!text){
				return;
			}
			socket.send(text);
			$("#chat-input").val("");
		}
	</script>

	<hr>
	<input type="text" id="chat-input">
	<button onclick="send();">전송</button>


</body>
</html>