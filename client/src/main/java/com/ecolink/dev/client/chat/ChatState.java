package com.ecolink.dev.client.chat;

public interface ChatState {
	
	String getName();
	void onCommand();
	void onGroup();
	void onUser();
	void onGlobal();
	void chat();
}
