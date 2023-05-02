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
	public String genGroupToken() {
		return UUID.randomUUID().toString().substring(0, 5);
	}

	@Override
	public void deleteGroup(GroupDTO groupDTO) throws SQLException {
		groupDao.deleteByToken(groupDTO.getToken());
	}

	@Override
	public GroupDTO findGroup(String tkGroup) {
		try {
			return groupDao.findByToken(tkGroup).toDTO();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void updateGroup(GroupDTO groupDTO, String password) throws SQLException {
		groupDao.update(groupDTO.toGroup());
	}

}
