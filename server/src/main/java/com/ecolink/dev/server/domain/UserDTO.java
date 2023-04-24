package com.ecolink.dev.server.domain;

import java.io.Serializable;

import com.ecolink.dev.server.domain.entity.User;

public class UserDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String id;
	public String token;
	public String name;
	public String password;
	
	public String getId() {
		return id;
	}

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public UserDTO(String token, String name, String password) {
		this.token = token;
		this.name = name;
		this.password = password;
	}
	
	public UserDTO(String id, String token, String name, String password) {
		super();
		this.id = id;
		this.token = token;
		this.name = name;
		this.password = password;
	}
	
	public User toUser() {
		return new User(this.id, 
				this.token, 
				this.name, 
				this.password);
	}
	
}
