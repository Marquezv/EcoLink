package com.ecolink.dev.server.services;

public interface MessageService {
	
	void unicastMessage(String messageToSend);
	void sendToTokens(String messageToSend, String tkUser);
	void broadcastMessage(String messageToSend);
//	void anyMessage(String messageToSend, List<ClientHandler> group);
	void close();

}
