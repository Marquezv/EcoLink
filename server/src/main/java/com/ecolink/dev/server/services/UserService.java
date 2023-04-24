package com.ecolink.dev.server.services;

import java.util.List;

import com.ecolink.dev.server.domain.UserDTO;

public interface UserService {
	
	 public UserDTO getUserByToken(String token) throws Exception;
	 public List<UserDTO> getAllUsers() throws Exception;
	 public void saveUser(UserDTO user) throws Exception;
	 public void deleteUser(String token) throws Exception;
	 public UserDTO login(String token, String password) throws Exception;
}