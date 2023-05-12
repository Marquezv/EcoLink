package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.List;

import com.ecolink.dev.server.domain.entity.AllowedGroupUser;

public interface AllowedGroupUserService {

	void addUser(String tkGroup, String tkUser) throws SQLException;
	
	void removeUser(String tkGroup, String tkUser) throws SQLException;
	
	void updateUserLevel(String tkGroup, String tkUser, Integer level) throws SQLException;
	
	String genAllowedId(String tkGroup, String tkUser);
	
	List<AllowedGroupUser> findGroup(String tkGroup) throws SQLException;
	
	boolean hasUser(String tkGroup, String tkUser) throws SQLException;
}

