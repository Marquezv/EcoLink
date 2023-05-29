package com.ecolink.dev.server.services;

import java.util.List;

import com.ecolink.dev.server.domain.UserDTO;

public interface UserService {
	
	UserDTO getUserByToken(String token) throws Exception;

	void sendUser(String tkUser, String message) throws Exception;
	
	List<UserDTO> getAllUsers() throws Exception;
	
	List<UserDTO> getAllUsersOnline() throws Exception;
	
	void saveUser(UserDTO user) throws Exception;

	void updateUser(UserDTO user) throws Exception;

	void deleteUser(String token) throws Exception;
	
	UserDTO login(String token, String password) throws Exception;

	String genUserToken();
}