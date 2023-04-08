package com.ecolink.dev.server.domain;

import java.io.Serializable;

import com.ecolink.dev.server.commons.status.LoginStatus;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String token;
	private String name;
	private String password;
	private LoginStatus loginStatus;
//	private Level level;
	private Sector location;
	private String office;
	
}
