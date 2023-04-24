package com.ecolink.dev.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.ecolink.dev.server.domain.Group;

public class GroupService {
	
	private List<Group> groups = new ArrayList<>();
	
	public void createGroup(String groupName, Integer limit, String admin, String admin_id) {
		Group group = new Group(groupName, limit, admin, admin_id);
		groups.add(group);
	}
	
//	public List<Map<String, String>> userGroup(String group_id) {
		
//	}
	
}
