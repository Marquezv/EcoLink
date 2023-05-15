package com.ecolink.dev.server.client.cli;

import java.net.Socket;

import com.ecolink.dev.server.client.ClientHandler;

public class Console {
	
	private ConsoleState state = new ConsoleCommand(this);
	private String tkConnection;
	private ClientHandler clientHandler;
	
	public ConsoleState getState() {
		return this.state;
	}
	
	public void setState(ConsoleState state)  {
		this.state = state;
	}
	
	public String getStateName(ConsoleState state) {
		return this.state.getName();
	}
	
	public void onCommand() {
		this.state.onCommand();
	}
	
	public void onMessage() {
		this.state.onMessage();
	}
	
	public void processInput(Socket socket, ClientHandler clientHanldler, String...args ) {
		this.state.processInput(socket, clientHandler, args);
	}

	public String getTkConnection() {
		return tkConnection;
	}

	public void setTkConnection(String tkConnection) {
		this.tkConnection = tkConnection;
	}

	public ClientHandler getClientHandler() {
		return clientHandler;
	}

	public void setClientHandler(ClientHandler clientHandler) {
		this.clientHandler = clientHandler;
	}
	
}