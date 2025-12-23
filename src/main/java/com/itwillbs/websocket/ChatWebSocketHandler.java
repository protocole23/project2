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
		roomSessions.computeIfAbsent(roomId, k -> ConcurrentHashMap.newKeySet()).add(session);
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Integer roomId = getRoomId(session);

		for (WebSocketSession s : roomSessions.get(roomId)) {
			if (s.isOpen()) {
				s.sendMessage(message);
			}
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		Integer roomId = getRoomId(session);
		Set<WebSocketSession> sessions = roomSessions.get(roomId);

		if (sessions != null) {
			sessions.remove(session);
		}
	}

	private Integer getRoomId(WebSocketSession session) {
		String uri = session.getUri().toString();
		String query = uri.substring(uri.indexOf("?") + 1);
		String[] params = query.split("&");

		for (String param : params) {
			String[] kv = param.split("=");
			if (kv[0].equals("roomId")) {
				return Integer.parseInt(kv[1]);
			}
		}
		return -1;
	}
}
