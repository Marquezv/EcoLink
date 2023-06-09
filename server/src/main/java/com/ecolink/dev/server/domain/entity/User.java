package com.ecolink.dev.server.domain.entity;

import java.util.UUID;

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
		this.id = UUID.randomUUID().toString().substring(0, 10);
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
