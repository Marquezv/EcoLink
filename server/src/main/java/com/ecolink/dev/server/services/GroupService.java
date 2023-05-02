package com.ecolink.dev.server.services;

import java.sql.SQLException;

import com.ecolink.dev.server.domain.GroupDTO;

public interface GroupService {

	void createGroup(GroupDTO groupDTO) throws SQLException;
	
	GroupDTO findGroup(String tkGroup);

	void updateGroup(GroupDTO groupDTO, String password) throws SQLException;

	void deleteGroup(GroupDTO groupDTO) throws SQLException;

	String genGroupToken();
}
