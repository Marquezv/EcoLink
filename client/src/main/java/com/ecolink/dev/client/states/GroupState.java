package com.ecolink.dev.client.states;

import com.ecolink.dev.client.Chat;

public class GroupState extends State{

	GroupState(Chat chat) {
		super(chat);
		chat.setConsole(false);
	}

	@Override
	public String onConsole() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String onGroup() {
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
