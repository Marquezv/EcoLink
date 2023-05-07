package com.ecolink.dev.client.chat;

import java.io.IOException;
import java.net.Socket;

import com.ecolink.dev.client.services.ClientService;
import com.ecolink.dev.client.services.ClientServiceImpl;

public class ChatGlobal implements ChatState {
	
	private String name = "ChatGlobal";
	private Chat chat;
	private ClientService clientService;
	
	
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
		System.out.println("[MODE - GLOBAL]");		
	}

	@Override
	public void processInput(Socket socket, String[] args) {
		try {
			clientService = new ClientServiceImpl(socket);
			clientService.sendString("send string true " + args.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
