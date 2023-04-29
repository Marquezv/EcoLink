package com.ecolink.dev.server.services;

import java.sql.SQLException;

import com.ecolink.dev.server.domain.GroupDTO;

public interface GroupService {

	void createGroup(GroupDTO groupDTO) throws SQLException;

	void addUser(GroupDTO groupDTO, String tkUser) throws SQLException;

	void join(GroupDTO groupDTO, String password) throws SQLException;

	void exit(GroupDTO groupDTO) throws SQLException;

	String genGroupToken();
}
