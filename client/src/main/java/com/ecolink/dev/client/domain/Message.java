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
	
	private String message;
	
	private String tkUser;
	private String tkGroup;
	
	public Message(String message, String tkUser, String tkGroup) {
		super();
		this.message = message;
		this.tkUser = tkUser;
		this.tkGroup = tkGroup;
	}
	
	
}