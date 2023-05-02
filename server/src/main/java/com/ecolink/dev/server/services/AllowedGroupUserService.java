package com.ecolink.dev.server.services;

import java.sql.SQLException;

import com.ecolink.dev.server.domain.GroupDTO;

public interface AllowedGroupUserService {

	void addUser(GroupDTO groupDTO, String tkUser) throws SQLException;
	
	void removeUser(GroupDTO groupDTO, String tkUser) throws SQLException;
	
	void updateUserLevel(GroupDTO groupDTO, String tkUser, Integer level) throws SQLException;
	
	String genAllowedId(GroupDTO groupDTO, String tkUser);
}
