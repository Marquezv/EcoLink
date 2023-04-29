package com.ecolink.dev.server.utils;

import com.ecolink.dev.server.ClientHandler;
import com.ecolink.dev.server.services.listener.UserCommand;

public class ListenerFactory {
	
	
	public ListenerFunction createStringFunction(ClientHandler clientHandler, String...args) {
		if(args[0].toString() == "user" || args[0].toString().equals("user")) {
			return new UserCommand(clientHandler);
		}else {
            throw new IllegalArgumentException("Unknown string function: " + args);
		}
	}	
	
}
