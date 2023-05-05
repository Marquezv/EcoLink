package com.ecolink.dev.client.chat;

public class Chat {
	
	private ChatState state = new ChatCommand(this);
	
	public ChatState getState() {
		return this.state;
	}
	
	public void setState(ChatState state)  {
		this.state = state;
		System.out.println("[CONSOLE_MODE:" + this.state.getName());
	}
	
	public String getStateName(ChatState state) {
		return this.state.getName();
	}
	
	public void onCommand() {
		this.state.onCommand();
	}
	
	public void onGroup() {
		this.state.onGroup();
	}
	
	public void onUser() {
		this.state.onUser();
	}
	
	public void onGlobal() {
		this.state.onGlobal();
	}
	
	public void chat() {
		this.state.chat();
	}
	
}
