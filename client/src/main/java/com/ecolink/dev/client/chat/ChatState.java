package com.ecolink.dev.client.chat;

import java.net.Socket;

public interface ChatState {
	
	String getName();
	void onCommand();
	void onGroup();
	void onUser();
	void onGlobal();
    void processInput(Socket socket,String[] args);
}
