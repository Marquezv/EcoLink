package com.ecolink.dev.server.client.cli;

import java.net.Socket;

import com.ecolink.dev.server.client.ClientHandler;

public class ConsoleCommand implements ConsoleState {
	
	private String name = "ConsoleCommand";
	private Console chat;
	
	public ConsoleCommand(Console chat) {
		super();
		this.chat = chat;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void onCommand() {
	}
	
	@Override
	public void onMessage() {
		this.chat.setState(new ConsoleMessage(this.chat));
	}

	@Override
	public void processInput(Socket socket, ClientHandler clientHandler, String[] args) {
//		CommandControl commandControl = new CommandControl(socket);
//		new CommandLine(commandControl)
//		.execute(args);			
	}
	
}