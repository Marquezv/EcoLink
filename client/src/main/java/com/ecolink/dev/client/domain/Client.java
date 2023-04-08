package com.ecolink.dev.client.domain;

import java.io.Serializable;
import java.util.UUID;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Client implements Serializable{

	private static final long serialVersionUID = 1L;
	
	
	private String token;
	
	private String name;
	
	private String pass;
	
	private String mensageBoxAddress;
	

	public void setName(String name) {
		this.name = name;
	}



	public void setPass(String pass) {
		this.pass = pass;
	}
	
	private void TokenServer () {
		this.token = UUID.randomUUID().toString().substring(0, 5);
		this.mensageBoxAddress = this.token;
	}



	public Client(String name, String pass) {
		super();
		this.name = name;
		this.pass = pass;
		TokenServer();
	}

}
