package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ecolink.dev.server.ClientHandler;
import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.domain.entity.User;
import com.ecolink.dev.server.repository.UserDao;

public class UserServiceImpl implements UserService {

	private UserDao userDao;
	private ClientHandler clientHandler;

	public UserServiceImpl(UserDao userDao, ClientHandler clientHandler) {
		this.userDao = userDao;
		this.clientHandler = clientHandler;
	}
	
	@Override
	public UserDTO login(String token, String password) throws Exception {
		User user = userDao.findByToken(token);

		if (user.getPassword().equals(password)) {
			return user.toDTO();
		}

		return null;
	}

	@Override
	public UserDTO getUserByToken(String token) throws SQLException {
		return userDao.findByToken(token).toDTO();
	}

	@Override
	public List<UserDTO> getAllUsers() throws SQLException {
		return userDao.findAll().stream().map(User::toDTO).collect(Collectors.toList());
	}
	
	@Override
	public void saveUser(UserDTO userDTO) throws SQLException {
		userDao.save(userDTO.toUser());
	}

	@Override
	public void deleteUser(String token) throws SQLException {
		userDao.deleteByToken(token);
	}

	@Override
	public String genUserToken() {
		return UUID.randomUUID().toString().substring(0, 7);
	}

	@Override
	public void updateUser(UserDTO userDTO) throws Exception {
		System.out.println("ServiceImpl - " + userDTO);

		userDao.update(userDTO.toUser());
	}

	@Override
	public List<UserDTO> getAllUsersOnline() throws Exception {
		List<UserDTO> usersOline = new ArrayList<>();
		for(ClientHandler client : clientHandler.getClientHandlers()) {
			if(client.getUserDTO() != null) {
				usersOline.add(client.getUserDTO());
			}
		}
		return usersOline;
	}

}
