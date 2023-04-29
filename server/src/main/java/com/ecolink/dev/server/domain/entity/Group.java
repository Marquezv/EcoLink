package com.ecolink.dev.server.domain.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Group {
	
	private String id;
	private String token;
	private String name;
	private String password;
	private String tkAdmin;
	private Integer userLimit;
	
	public Group(String id, String token, String name, String password, String tkAdmin, Integer userLimit) {
		super();
		this.id = UUID.randomUUID().toString().substring(0, 10);
		this.token = token;
		this.name = name;
		this.password = password;
		this.tkAdmin = tkAdmin;
		this.userLimit = userLimit;
	}
	
}
