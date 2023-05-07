package com.ecolink.dev.client.chat;

import java.net.Socket;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;

public class ChatCommand implements ChatState {
	
	private String name = "ChatCommand";
	private Chat chat;
	
	public ChatCommand(Chat chat) {
		super();
		this.chat = chat;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void onCommand() {
		System.out.println("[MODE - CONSOLE]");
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
		this.chat.setState(new ChatGlobal(this.chat));
	}

	@Override
	public void processInput(Socket socket, String[] args) {
		CommandControl commandControl = new CommandControl(socket);
		new CommandLine(commandControl)
		.execute(args);			
	}

}
