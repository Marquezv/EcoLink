package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.UUID;

import com.ecolink.dev.server.domain.GroupDTO;
import com.ecolink.dev.server.repository.GroupDao;

public class GroupServiceImpl implements GroupService {

	private GroupDao groupDao;

	public GroupServiceImpl(GroupDao groupDao) {
		this.groupDao = groupDao;
	}

	@Override
	public void createGroup(GroupDTO groupDTO) throws SQLException {
		groupDao.save(groupDTO.toGroup());
	}

	@Override
	public void addUser(GroupDTO groupDTO, String tkUser) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public void join(GroupDTO groupDTO, String password) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public void exit(GroupDTO groupDTO) throws SQLException {
		// TODO Auto-generated method stub

	}

	@Override
	public String genGroupToken() {
		return UUID.randomUUID().toString().substring(0, 5);
	}

}
