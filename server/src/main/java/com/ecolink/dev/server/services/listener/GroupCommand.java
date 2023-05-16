package com.ecolink.dev.server.services.listener;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ecolink.dev.server.client.ClientHandler;
import com.ecolink.dev.server.domain.GroupDTO;
import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.repository.GroupDao;
import com.ecolink.dev.server.services.GroupService;
import com.ecolink.dev.server.services.GroupServiceImpl;
import com.ecolink.dev.server.services.MessageService;
import com.ecolink.dev.server.services.MessageServiceImpl;
import com.ecolink.dev.server.utils.ListenerFunction;

public class GroupCommand implements ListenerFunction {

	// String [group] ... ... ...
	// String [group] [allowed] ... ... ...
	private ClientHandler clientHandler;
	private GroupService groupService;
	private MessageService messageService;

	public GroupCommand(ClientHandler clientHandler) {
		super();
		this.clientHandler = clientHandler;
		this.messageService = new MessageServiceImpl(clientHandler);
		this.groupService = new GroupServiceImpl(new GroupDao(), clientHandler);
	}

	@Override
	public void apply(String... args) {
		if (args[1].toString() == "create-group" || args[1].toString().equals("create-group")) {
			create(args);
		}
		if (args[1].toString() == "add" || args[1].toString().equals("add")) {
			add(args);
		}
		if (args[1].toString() == "join" || args[1].toString().equals("join")) {
			join(args);
		}
		if (args[1].toString() == "open" || args[1].toString().equals("open")) {
			open(args);
		}
		if (args[1].toString() == "list" || args[1].toString().equals("list")) {
			list();
		}
	}

	private void create(String... args) {
		UserDTO userDTO = clientHandler.getUserDTO();
		System.out.println(userDTO);
		if (userDTO != null) {
			String token = groupService.genGroupToken();
			String name = args[2].toString();
			String password = args[3].toString();
			String tkAdmin = userDTO.getToken();
			Integer userLimit = Integer.parseInt(args[4].toString());
			try {
				GroupDTO groupDTO = new GroupDTO(token, name, password, tkAdmin, userLimit);
				groupService.createGroup(groupDTO);
				messageService.unicastMessage("[GROUP] token: " + groupDTO.getToken() + " name: " + groupDTO.getName());
			} catch (SQLException e) {
				e.printStackTrace();
				messageService.unicastMessage("[ERROR] Create group");
			}
		} else {
			messageService.unicastMessage("User not loged");
		}

	}

	private void add(String... args) {
		String tkGroup = args[2].toString();
		String tkUser = args[3].toString();
		try {
			System.out.println("Add " + tkUser + " on " + tkGroup);
			groupService.addUser(tkGroup, tkUser);
		} catch (SQLException e) {
			messageService.unicastMessage("GROUP NOT FOUND");
			e.printStackTrace();
		}
	}

	private void open(String... args) {
		String tkGroup = args[2].toString();

		try {
			GroupDTO groupDTO = groupService.findGroup(tkGroup);
			groupService.openGroup(groupDTO);
		} catch (Exception e) {
			messageService.unicastMessage("GROUP NOT FOUND");
		}
	}
	
	private void join(String... args) {
		String tkGroup = args[2].toString();
		try {
			GroupDTO groupDTO = groupService.findGroup(tkGroup);
			
			messageService.unicastMessage("-------GROUP: " + groupDTO.getName() + " | " + groupDTO.getToken() + " -------");
		} catch (Exception e) {
			messageService.unicastMessage("GROUP NOT FOUND");
		}
	}
	
	private void list() {
		List<GroupDTO> userList = new ArrayList<>();
		try {
			userList = groupService.getAllGroups();
			messageService.unicastMessage(formatList(userList));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String formatList(List<GroupDTO> list) {
		List<String> listFormat = new ArrayList<String>();
		for (GroupDTO groupDTO : list) {
			listFormat.add("token:" + groupDTO.getToken() + "|user:" + groupDTO.getName());
		}
		return listFormat.stream().map(item -> "\n* " + item).collect(Collectors.joining("\n"));
	}
	
}
