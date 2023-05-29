package com.ecolink.dev.server.client.cli;

import java.net.Socket;

import com.ecolink.dev.server.client.ClientHandler;
import com.ecolink.dev.server.repository.GroupDao;
import com.ecolink.dev.server.services.GroupServiceImpl;

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
	public void processInput(Socket socket,  ClientHandler clientHandler, String[] args) {
		try {
			String reversedMessage = String.join(" ", args);
			new GroupServiceImpl(new GroupDao(), chat.getClientHandler()).sendGroup(chat.getTkConnection(), reversedMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}