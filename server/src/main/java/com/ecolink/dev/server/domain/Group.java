package com.ecolink.dev.server.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Group implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String groupName;
	private Integer limit;
	private String admin;
	private String admin_id;
	private List<Map<String, String>> group = new ArrayList<>();
	
	public Group(String groupName, Integer limit, String admin, String admin_id) {
		super();
		this.groupName = groupName;
		this.limit = limit;
		this.admin = admin;
		this.admin_id = admin_id;
	}
	
}


