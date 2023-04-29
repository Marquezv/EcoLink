package com.ecolink.dev.server.services;

import java.util.ArrayList;
import java.util.List;

import com.ecolink.dev.server.domain.GroupDTO;

public class GroupService {
	
	private List<GroupDTO> groups = new ArrayList<>();
	
	public void createGroup(String groupName, Integer limit, String admin, String admin_id) {
		GroupDTO group = new GroupDTO(groupName, limit, admin, admin_id);
		groups.add(group);
	}
	
//	public List<Map<String, String>> userGroup(String group_id) {
		
//	}
	
}
