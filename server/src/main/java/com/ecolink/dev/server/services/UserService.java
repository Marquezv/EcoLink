package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.List;

import com.ecolink.dev.server.domain.User;

public interface UserService {
	
	 public User getUserByToken(String token) throws SQLException;
	 public List<User> getAllUsers() throws SQLException;
	 public void saveUser(User user) throws SQLException;
	 public void deleteUser(String token) throws SQLException;
	
}