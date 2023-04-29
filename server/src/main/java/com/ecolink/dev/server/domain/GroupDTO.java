package com.ecolink.dev.server.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class GroupDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String id;
	private String token;
	private String name;
	private String password;
	private String tkAdmin;
	private Integer userLimit;
	
	public GroupDTO(String id, String token, String name, String password, String tkAdmin, Integer userLimit) {
		super();
		this.id = id;
		this.token = token;
		this.name = name;
		this.password = password;
		this.tkAdmin = tkAdmin;
		this.userLimit = userLimit;
	}

}


