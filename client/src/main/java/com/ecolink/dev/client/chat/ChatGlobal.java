package com.ecolink.dev.client.chat;

import java.net.Socket;

public class ChatGlobal implements ChatState {
	
	private String name = "ChatGlobal";
	private Chat chat;
	
	public ChatGlobal(Chat chat) {
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
		this.chat.setState(new ChatGroup(this.chat));
	}

	@Override
	public void onUser() {
		this.chat.setState(new ChatUser(this.chat));
	}

	@Override
	public void onGlobal() {
		System.out.println("[MODE - GROUP]");		
	}

	@Override
	public void processInput(Socket socket, String[] args) {
		System.out.println(args.toString());
		
	}

}
