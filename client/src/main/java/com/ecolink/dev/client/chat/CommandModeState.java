package com.ecolink.dev.client.chat;

import java.net.Socket;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;

public class CommandModeState implements ConsoleModeState {

	CommandControl commandControl;
	
	@Override
	public void processInput(String input, Socket socket) {
		String[] args = input.split(" ");
		CommandControl commandControl = new CommandControl(socket);
		new CommandLine(commandControl)
		.execute(args);	
	}

}
