package com.ecolink.dev.client.console;

import java.net.Socket;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;

public class ConsoleCommand implements ConsoleState {
	
	private String name = "ChatCommand";
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
		System.out.println("[MODE - COMMAND]");
	}
	
	@Override
	public void onMessage() {
		this.chat.setState(new ConsoleMessage(this.chat));
	}

	@Override
	public void processInput(Socket socket, String[] args) {
		CommandControl commandControl = new CommandControl(socket);
		new CommandLine(commandControl)
		.execute(args);			
	}

	

}
