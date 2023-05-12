package com.ecolink.dev.server.client.cli;

import java.net.Socket;

public class ConsoleMessage implements ConsoleState {
	
	private String name = "ConsoleMessage";
	private Console chat;
//	private ClientService clientService;
	
	public ConsoleMessage(Console chat) {
		super();
		this.chat = chat;
//		this.clientService = new ClientServiceImpl(socket);

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
	public void processInput(Socket socket, String[] args) {
		try {
			String reversedMessage = String.join(" ", args);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}