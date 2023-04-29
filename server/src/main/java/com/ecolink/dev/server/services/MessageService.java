package com.ecolink.dev.server.services;

import java.util.List;

import com.ecolink.dev.server.ClientHandler;

public interface MessageService {
	
	void unicastMessage(String messageToSend);
	void sendToTokens(String messageToSend, String tkUser);
	void broadcastMessage(String messageToSend, List<ClientHandler> group);
	void anyMessage(String messageToSend, List<String> group);
	void close();

}
