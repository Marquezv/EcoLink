package com.ecolink.dev.client.states;

import com.ecolink.dev.client.Chat;

public class ConsoleState extends State{

	public ConsoleState(Chat chat) {
		super(chat);
		chat.setConsole(true);
	}

	@Override
	public String onConsole() {
		String action = chat.startConsole();
		chat.changeState(new ConsoleState(chat));
		return action;
	}

	@Override
	public String onGroup() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String onPrivate() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String onGlobal() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
