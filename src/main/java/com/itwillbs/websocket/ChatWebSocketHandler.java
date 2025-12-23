package com.itwillbs.websocket;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

public class ChatWebSocketHandler extends TextWebSocketHandler {

	private static final Map<Integer, Set<WebSocketSession>> roomSessions = new ConcurrentHashMap<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		Integer roomId = getRoomId(session);
		if (roomId == null) {
			session.close(); // roomId 없으면 연결 종료
			return;
		}
		roomSessions.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Integer roomId = getRoomId(session);
		if (roomId == null) return;
		

		Set<WebSocketSession> sessions = roomSessions.get(roomId);
		if (sessions != null) {
			for (WebSocketSession s : sessions) {
				if (s.isOpen()) {
					s.sendMessage(message);
				}
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Integer roomId = getRoomId(session);
		if (roomId == null) return;

		Set<WebSocketSession> sessions = roomSessions.get(roomId);
		if (sessions != null) {
			sessions.remove(session);
			if (sessions.isEmpty()) {
				roomSessions.remove(roomId); 
			}
		}
	}

	private Integer getRoomId(WebSocketSession session) {
		try {
			String query = session.getUri().getQuery(); // "roomId=123"
			if (query != null) {
				for (String param : query.split("&")) {
					String[] kv = param.split("=");
					if (kv.length == 2 && kv[0].equals("roomId")) {
						return Integer.parseInt(kv[1]);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
