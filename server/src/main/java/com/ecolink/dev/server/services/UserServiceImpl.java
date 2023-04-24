package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.domain.entity.User;
import com.ecolink.dev.server.repository.UserDao;

public class UserServiceImpl implements UserService{
	
	private UserDao userDao;
	
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public UserDTO login(String token, String password) throws Exception {
		User user =  userDao.findByToken(token);
		if(user.getPassword() == password) {
			return user.toDTO();
		}
		return null;
	}
	
	
	@Override
	public UserDTO getUserByToken(String token) throws SQLException {
		return 	userDao.findByToken(token).toDTO();
	}

	@Override
	public List<UserDTO> getAllUsers() throws SQLException {
		return userDao.findAll().stream().map(User::toDTO).collect(Collectors.toList());
	}

	@Override
	public void saveUser(UserDTO userDTO) throws SQLException {
		userDTO.setToken(userToken());
		userDao.save(userDTO.toUser());
	}

	@Override
	public void deleteUser(String token) throws SQLException {
		userDao.deleteByToken(token);
	}
	
	private String userToken() {
	    return UUID.randomUUID().toString().substring(0, 5);
	}
	
}
