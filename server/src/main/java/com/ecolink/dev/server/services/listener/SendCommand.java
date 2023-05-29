package com.ecolink.dev.server.services.listener;

import com.ecolink.dev.server.client.ClientHandler;
import com.ecolink.dev.server.services.MessageService;
import com.ecolink.dev.server.services.MessageServiceImpl;
import com.ecolink.dev.server.utils.ListenerFunction;

public class SendCommand implements ListenerFunction{
	
	//String =[send] ... ... ... 
	private MessageService messageService;
	
	public SendCommand(ClientHandler clientHandler) {
		super();
		this.messageService = new MessageServiceImpl(clientHandler);
	}
	
	@Override
	public void apply(String... args) {
		if(args[1].toString() == "string" || args[1].toString().equals("string")) {
			sendString(args);
		}
	}

	private void sendString(String...args) {
		String global = args[2].toString();
		String tkUser = args[3].toString();
		String message = args[4].toString();
		
		if(global == "true" || global.equals("true")) {
			messageService.broadcastMessage(message);
		}
		else {
			messageService.sendToTokens(message, tkUser);
		}
	}
	

}
