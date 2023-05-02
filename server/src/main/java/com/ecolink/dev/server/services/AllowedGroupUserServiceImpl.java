package com.ecolink.dev.server.services;

import java.sql.SQLException;

import com.ecolink.dev.server.domain.GroupDTO;
import com.ecolink.dev.server.domain.entity.AllowedGroupUser;
import com.ecolink.dev.server.repository.AllowedGroupUserDao;

public class AllowedGroupUserServiceImpl implements AllowedGroupUserService {

	private AllowedGroupUserDao allowedGroupUserDao;

	public AllowedGroupUserServiceImpl(AllowedGroupUserDao allowedGroupUserDao) {
		this.allowedGroupUserDao = allowedGroupUserDao;
	}

	@Override
	public void addUser(GroupDTO groupDTO, String tkUser) throws SQLException {
		try {
			String id_allowed = genAllowedId(groupDTO, tkUser);
			AllowedGroupUser allowedGroupUser = new AllowedGroupUser(
					id_allowed, groupDTO.getToken(), tkUser, 0);
			allowedGroupUserDao.save(allowedGroupUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUser(GroupDTO groupDTO, String tkUser) throws SQLException {
		try {
			String id_allowed = genAllowedId(groupDTO, tkUser);
			allowedGroupUserDao.deleteByToken(id_allowed);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserLevel(GroupDTO groupDTO, String tkUser, Integer level) throws SQLException {
		try {
			String id_allowed = genAllowedId(groupDTO, tkUser);
			AllowedGroupUser allowed = allowedGroupUserDao.findByToken(id_allowed);
			allowed.setLevel(level);
			allowedGroupUserDao.update(allowed);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String genAllowedId(GroupDTO groupDTO, String tkUser) {
		return groupDTO.getId() + "-" + tkUser;
	}
	
	
}
