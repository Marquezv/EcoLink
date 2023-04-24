package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.List;

import com.ecolink.dev.server.domain.User;
import com.ecolink.dev.server.repository.UserDao;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public User getUserByToken(String token) throws SQLException {
		return userDao.findByToken(token);
	}

	@Override
	public List<User> getAllUsers() throws SQLException {
		return userDao.findAll();
	}

	@Override
	public void saveUser(User user) throws SQLException {
		userDao.save(user);
	}

	@Override
	public void deleteUser(String token) throws SQLException {
		userDao.deleteByToken(null);
	}
	
}
