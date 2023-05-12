package com.ecolink.dev.server.services;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.ecolink.dev.server.client.ClientHandler;
import com.ecolink.dev.server.client.cli.Console;
import com.ecolink.dev.server.domain.GroupDTO;
import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.domain.entity.Group;
import com.ecolink.dev.server.repository.AllowedGroupUserDao;
import com.ecolink.dev.server.repository.GroupDao;
import com.ecolink.dev.server.repository.UserDao;

public class GroupServiceImpl implements GroupService {

	private GroupDao groupDao;
	private MessageService messageService;
	private AllowedGroupUserService allowedGroupUserService;
	private UserService userService;
	private Console console;
	private UserDTO user;
	
	public GroupServiceImpl(GroupDao groupDao, ClientHandler clientHandler) {
		this.groupDao = groupDao;
		this.messageService = new MessageServiceImpl(clientHandler);
		this.allowedGroupUserService = new AllowedGroupUserServiceImpl(new AllowedGroupUserDao());
		this.userService = new UserServiceImpl(new UserDao(), clientHandler);
		this.user = clientHandler.getUserDTO();
		this.console = clientHandler.getConsole();
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
	public void deleteGroup(String tkGrop) throws SQLException {
		String tkGroup = groupDao.findByToken(tkGrop).getToken();
		groupDao.deleteByToken(tkGroup);
	}
	
	@Override
	public List<GroupDTO> getAllGroups() throws SQLException {
		return groupDao.findAll().stream().map(Group::toDTO).collect(Collectors.toList());
	}

	@Override
	public GroupDTO findGroup(String tkGroup) {
		try {
			return groupDao.findByToken(tkGroup).toDTO();
		} catch (SQLException e) {
			messageService.unicastMessage("USER NOT FOUND IN GROUP:" + tkGroup);
		}
		return null;
	}

	@Override
	public void updateGroup(String tkGrop, String password) throws SQLException {
		Group group = groupDao.findByToken(tkGrop);
		groupDao.update(group);
	}

	@Override
	public void addUser(String tkGrop, String tkUser) throws SQLException {
		try {
			GroupDTO group = findGroup(tkGrop);
			UserDTO user = userService.getUserByToken(tkUser);
			allowedGroupUserService.addUser(group.getToken(), user.getToken());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void openGroup(GroupDTO groupDTO) throws Exception {
		try {
			allowedGroupUserService.hasUser(groupDTO.getToken(), user.getToken());
			messageService.unicastMessage("-------GROUP: " + groupDTO.getName() + " | " + groupDTO.getToken() + " -------");
			console.onMessage();
			console.setTkConnection(groupDTO.getToken());
		} catch (Exception e){
			messageService.unicastMessage("[ERROR - OPEN_GROUP]");
		}
		
		
	}

}
