package com.ecolink.dev.client.states;

import com.ecolink.dev.client.Chat;

public abstract	class State {
	
	Chat chat;
	
	State(Chat chat) {
		this.chat = chat;
	}
	
	public abstract String onConsole();
	public abstract String onGroup();
	public abstract String onPrivate();
	public abstract String onGlobal();
	
}
