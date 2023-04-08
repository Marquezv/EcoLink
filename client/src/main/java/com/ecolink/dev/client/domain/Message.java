package com.ecolink.dev.client.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String messageToken;
	
	private String senderToken;
	
	private String addressee;

	public Message(String messageToken, String senderToken, String addressee) {
		super();
		this.messageToken = messageToken;
		this.senderToken = senderToken;
		this.addressee = addressee;
		TokenServer();
	}

	
	private void TokenServer () {
		this.messageToken = UUID.randomUUID().toString().substring(0, 5);
	}
}
