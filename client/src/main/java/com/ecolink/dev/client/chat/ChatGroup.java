package com.ecolink.dev.client.chat;

import java.net.Socket;

public class ChatGroup implements ChatState {
	
	private String name = "ChatGroup";
	private Chat chat;
	
	public ChatGroup(Chat chat) {
		super();
		this.chat = chat;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void onCommand() {
		this.chat.setState(new ChatCommand(this.chat));
	}

	@Override
	public void onGroup() {
		System.out.println("[MODE - GROUP]");
	}

	@Override
	public void onUser() {
		this.chat.setState(new ChatUser(this.chat));
	}

	@Override
	public void onGlobal() {
		this.chat.setState(new ChatGlobal(this.chat));
	}

	@Override
	public void processInput(Socket socket, String[] args) {
	}
}
