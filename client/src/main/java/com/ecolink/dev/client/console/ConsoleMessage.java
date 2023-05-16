package com.ecolink.dev.client.console;

import java.io.IOException;
import java.net.Socket;

import com.ecolink.dev.client.services.ClientServiceImpl;

public class ConsoleMessage implements ConsoleState {
	
	private String name = "ChatMessage";
	private Console chat;
	
	public ConsoleMessage(Console chat) throws IOException {
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
		System.out.println("[MODE - MESSAGE]");
	}

	@Override
	public void processInput(Socket socket, String[] args) {
		
		try {
			String reversedMessage = String.join(" ", args);
			new ClientServiceImpl(socket).sendString(reversedMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}