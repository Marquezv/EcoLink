package com.ecolink.dev.server.domain.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AllowedGroupUser {
		
	private String id;
	private String tkGroup;
	private String tkUser;
	private Integer level;
	
	public AllowedGroupUser(String id, String tkGroup, String tkUser, Integer level) {
		super();
		this.id = UUID.randomUUID().toString().substring(0, 10);
		this.tkGroup = tkGroup;
		this.tkUser = tkUser;
		this.level = level;
	}
	
	
}
