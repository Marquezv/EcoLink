package com.ecolink.dev.server.client.cli;

import java.net.Socket;

public class Console {
	
	private ConsoleState state = new ConsoleCommand(this);
	//tkGroup or TkUser
	private String tkConnection;
	
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
	
	public void processInput(Socket socket, String...args) {
		this.state.processInput(socket, args);
	}

	public String getTkConnection() {
		return tkConnection;
	}

	public void setTkConnection(String tkConnection) {
		this.tkConnection = tkConnection;
	}
	
}