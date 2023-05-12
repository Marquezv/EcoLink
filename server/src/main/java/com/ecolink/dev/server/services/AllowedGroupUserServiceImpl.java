package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.List;

import com.ecolink.dev.server.domain.entity.AllowedGroupUser;
import com.ecolink.dev.server.repository.AllowedGroupUserDao;

public class AllowedGroupUserServiceImpl implements AllowedGroupUserService {

	private AllowedGroupUserDao allowedGroupUserDao;
	
	public AllowedGroupUserServiceImpl(AllowedGroupUserDao allowedGroupUserDao) {
		this.allowedGroupUserDao = allowedGroupUserDao;
	}

	@Override
	public void addUser(String tkGroup, String tkUser) throws SQLException {
		try {
			if(!hasUser(tkGroup, tkUser)) {
				String id_allowed = genAllowedId(tkGroup, tkUser);
				AllowedGroupUser allowedGroupUser = new AllowedGroupUser(
						id_allowed, tkGroup, tkUser, 0);
				allowedGroupUserDao.save(allowedGroupUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void removeUser(String tkGroup, String tkUser) throws SQLException {
		try {
			String id_allowed = genAllowedId(tkGroup, tkUser);
			allowedGroupUserDao.deleteByToken(id_allowed);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updateUserLevel(String tkGroup, String tkUser, Integer level) throws SQLException {
		try {
			String id_allowed = genAllowedId(tkGroup, tkUser);
			AllowedGroupUser allowed = allowedGroupUserDao.findByToken(id_allowed);
			allowed.setLevel(level);
			allowedGroupUserDao.update(allowed);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String genAllowedId(String tkGroup, String tkUser) {
		return tkGroup + "-" + tkUser;
	}

	@Override
	public List<AllowedGroupUser> findGroup(String tkGroup) throws SQLException {
		return allowedGroupUserDao.findGroup(tkGroup);
	}
	
	@Override
	public boolean hasUser(String tkGroup, String tkUser) throws SQLException {
		return findGroup(tkGroup).stream().anyMatch(allowed -> allowed.getTkUser().equals(tkUser));
	}
	
}
