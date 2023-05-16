package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.List;

import com.ecolink.dev.server.domain.GroupDTO;

public interface GroupService {

	void createGroup(GroupDTO groupDTO) throws SQLException;
	
	void addUser(String tkGrop, String tkUser) throws SQLException;
	
	void openGroup(GroupDTO groupDTO) throws Exception;
	
	void sendGroup(String tkGrop, String messsage) throws Exception;
	
	GroupDTO findGroup(String tkGroup);
	
	List<GroupDTO> getAllGroups() throws SQLException;
	
	void updateGroup(String tkGrop, String password) throws SQLException;

	void deleteGroup(String tkGrop) throws SQLException;

	String genGroupToken();
}
