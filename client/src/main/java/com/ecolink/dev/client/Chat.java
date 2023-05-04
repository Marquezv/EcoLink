package com.ecolink.dev.client;

import com.ecolink.dev.client.states.ConsoleState;
import com.ecolink.dev.client.states.State;

public class Chat {
	
	private State state;
	private boolean console = false;
	//current
	
	public Chat() {
		this.state = new ConsoleState(this);
		setConsole(true);
	}
	
	public String startConsole() {
		return "[ MODE | CONSOLE ]";
	}
	
	public void changeState(State state) {
		this.state = state;
	}
	
	public State getState() {
		return state;
	}
	
	public void setConsole(boolean console) {
		this.console = console;
	}
	
	public boolean isConsole() {
		return console;
	}
	
}
