package com.ecolink.dev.client.chat;

public class ChatCommand implements ChatState {
	
	private String name = "ChatCommand";
	private Chat chat;
	
	public ChatCommand(Chat chat) {
		super();
		this.chat = chat;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void onCommand() {
		System.out.println("[MODE - CONSOLE]");
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void chat() {
		// TODO Auto-generated method stub
		
	}

}
