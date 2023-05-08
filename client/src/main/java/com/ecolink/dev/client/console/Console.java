package com.ecolink.dev.client.console;

import java.net.Socket;

public class Console {
	
	private ConsoleState state = new ConsoleCommand(this);
	
	public ConsoleState getState() {
		return this.state;
	}
	
	public void setState(ConsoleState state)  {
		this.state = state;
		System.out.println("[CONSOLE_MODE:" + this.state.getName());
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
	
}
