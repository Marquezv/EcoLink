package com.ecolink.dev.server.client.cli;

import java.net.Socket;

import com.ecolink.dev.server.client.ClientHandler;

public class ConsoleMessage implements ConsoleState {
	
	private String name = "ConsoleMessage";
	private Console chat;
	
	public ConsoleMessage(Console chat) {
		super();
		this.chat = chat;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void onCommand() {
		this.chat.setState(new ConsoleCommand(this.chat));
	}
	
	@Override
	public void onMessage() {
	}

	@Override
	public void processInput(Socket socket, ClientHandler clientHandler, String[] args) {
		try {
	        String message = String.join(" ", args);
	        System.out.println("Message: " + message + " From: " + clientHandler.getUserDTO().getName() + " Group: " + chat.getTkConnection());
//			new GroupServiceImpl(new GroupDao(), clientHandler).sendGroup(chat.getTkConnection(), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}