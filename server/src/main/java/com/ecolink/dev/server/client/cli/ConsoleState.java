package com.ecolink.dev.server.client.cli;

import java.net.Socket;

import com.ecolink.dev.server.client.ClientHandler;

public interface ConsoleState {
	
	String getName();
	void onCommand();
	void onMessage();
    void processInput(Socket socket, ClientHandler clientHandler,String[] args);
    
}