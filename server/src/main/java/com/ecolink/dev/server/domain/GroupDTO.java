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
public class GroupDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String groupName;
	private Integer limit;
	private String tkAdmin;
	private List<Map<String, String>> users = new ArrayList<>();
	
	public GroupDTO(String groupName, Integer limit, String admin, String tkAdmin) {
		super();
		this.groupName = groupName;
		this.limit = limit;
		this.tkAdmin = tkAdmin;
	}
	
}


