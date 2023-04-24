package com.ecolink.dev.server.domain.entity;

import com.ecolink.dev.server.domain.UserDTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
	
	private String id;
	private String token;
	public String name;
	public String password;
	
	public User(String id, String token, String name, String password) {
		super();
		this.id = id;
		this.token = token;
		this.name = name;
		this.password = password;
	}
	
	public UserDTO toDTO() {
		return new UserDTO(
				this.id,
				this.token,
				this.name,
				this.password);
	}
	
}
