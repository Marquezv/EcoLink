package com.ecolink.dev.server.services;

import java.util.ArrayList;
import java.util.List;

import com.ecolink.dev.server.domain.GroupDTO;

public class GroupService {
	
	private List<GroupDTO> groups = new ArrayList<>();
	
	public void createGroup(String groupName, Integer userLimit, String tkAdmin, String password) {
		String id = null;
		String token = null;
		GroupDTO groupDTO = new GroupDTO(id,token, groupName, password, tkAdmin, userLimit);
		groups.add(groupDTO);
	}
	
}
