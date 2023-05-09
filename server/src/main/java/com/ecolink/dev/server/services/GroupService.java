package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.List;

import com.ecolink.dev.server.domain.GroupDTO;

public interface GroupService {

	void createGroup(GroupDTO groupDTO) throws SQLException;
	
	GroupDTO findGroup(String tkGroup);
	
	List<GroupDTO> getAllGroups() throws SQLException;
	
	void updateGroup(GroupDTO groupDTO, String password) throws SQLException;

	void deleteGroup(GroupDTO groupDTO) throws SQLException;

	String genGroupToken();
}
