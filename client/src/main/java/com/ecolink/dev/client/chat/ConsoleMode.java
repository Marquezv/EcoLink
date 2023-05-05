package com.ecolink.dev.client.chat;

import java.net.Socket;

public class ConsoleMode {
	
	private ConsoleModeState state;
	
	public void setState(ConsoleModeState state) {
		this.state = state;
	}
	
	public void processInput(String input, Socket socket) {
		state.processInput(input, socket);
	}

}
