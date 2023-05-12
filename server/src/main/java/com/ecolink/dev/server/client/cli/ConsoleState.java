package com.ecolink.dev.server.client.cli;

import java.net.Socket;

public interface ConsoleState {
	
	String getName();
	void onCommand();
	void onMessage();
    void processInput(Socket socket,String[] args);
    
}