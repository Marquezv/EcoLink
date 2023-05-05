package com.ecolink.dev.client.chat;

public class ChatUser implements ChatState {
	
	private String name = "ChatUser";
	private Chat chat;
	
	public ChatUser(Chat chat) {
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
		System.out.println("[MODE - USER]");
	}

	@Override
	public void onGlobal() {
		this.chat.setState(new ChatGlobal(this.chat));
	}

	@Override
	public void chat() {
		// TODO Auto-generated method stub
		
	}

}
