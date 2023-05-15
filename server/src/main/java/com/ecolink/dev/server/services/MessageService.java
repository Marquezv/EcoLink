package com.ecolink.dev.server.services;

import java.util.List;

public interface MessageService {
	
	void queueMessage(String messageToSend, String tk);
	void unicastMessage(String messageToSend);
	void sendToTokens(String messageToSend, String tkUser);
	void broadcastMessage(String messageToSend);
	void anyMessage(String messageToSend, List<String> group, String tkGroup); //List users:Tokens
	void close();

}
