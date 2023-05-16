package com.ecolink.dev.server.client.cli;

import java.net.Socket;

import com.ecolink.dev.server.client.ClientHandler;

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
	public void processInput(Socket socket,  ClientHandler clientHandler, String[] args) {
		try {
			String reversedMessage = String.join(" ", args);
			System.out.println(reversedMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}