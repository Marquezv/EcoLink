package com.ecolink.dev.client.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Message implements Serializable{

private static final long serialVersionUID = 1L;
	
	private String message_content;

	
	public Message(String message_content) {
		this.message_content = message_content;
	}

}