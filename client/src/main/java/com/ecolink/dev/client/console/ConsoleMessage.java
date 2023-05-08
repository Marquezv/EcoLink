package com.ecolink.dev.client.console;

import java.net.Socket;

import com.ecolink.dev.client.services.ClientService;

public class ConsoleMessage implements ConsoleState {
	
	private String name = "ChatGroup";
	private Console chat;
	private ClientService clientService;
	
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
		System.out.println("[MODE - MESSAGE]");
	}

	@Override
	public void processInput(Socket socket, String[] args) {
		try {
			String reversedMessage = String.join(" ", args);
			System.out.println("Send " + reversedMessage);
			clientService.sendString(reversedMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
}
