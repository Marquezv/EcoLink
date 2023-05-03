package com.ecolink.dev.server.services.listener;

import java.sql.SQLException;

import com.ecolink.dev.server.ClientHandler;
import com.ecolink.dev.server.domain.GroupDTO;
import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.repository.AllowedGroupUserDao;
import com.ecolink.dev.server.repository.GroupDao;
import com.ecolink.dev.server.services.AllowedGroupUserService;
import com.ecolink.dev.server.services.AllowedGroupUserServiceImpl;
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
	private AllowedGroupUserService allowedGroupUserService;
	
	public GroupCommand(ClientHandler clientHandler) {
		super();
		this.clientHandler = clientHandler;
		this.messageService = new MessageServiceImpl(clientHandler);
		this.groupService = new GroupServiceImpl(new GroupDao());
		this.allowedGroupUserService = new AllowedGroupUserServiceImpl(new AllowedGroupUserDao());
	}

	@Override
	public void apply(String... args) {
		if (args[1].toString() == "create-group" || args[1].toString().equals("create-group")) {
			create(args);
		}
		if (args[1].toString() == "add" || args[1].toString().equals("add")) {
			add(args);
		}
		if (args[1].toString() == "open" || args[1].toString().equals("join")) {
			open(args);
		}
	}

	private void create(String...args)  {
		UserDTO userDTO = clientHandler.getUserDTO();
		System.out.println(userDTO);
		if( userDTO != null) {
			String token = groupService.genGroupToken();
			String name = args[2].toString();
			String password = args[3].toString();
			String tkAdmin = userDTO.getToken();
			Integer userLimit = Integer.parseInt(args[4].toString());
			
			try {
				GroupDTO groupDTO = new GroupDTO(token, name, password, tkAdmin, userLimit);
				groupService.createGroup(groupDTO);
				messageService.unicastMessage("[GROUP] token: " + groupDTO.getToken() + 
						" name: " + groupDTO.getName());
			} catch (SQLException e) {
				e.printStackTrace();
				messageService.unicastMessage("[ERROR] Create group");
			}
		}else {
			messageService.unicastMessage("User not loged");
		}
		
	}
	
	private void add(String...args) {
		String tkGroup = args[2].toString(); 
		String tkUser = args[3].toString();
		
		try {
			GroupDTO groupDTO = groupService.findGroup(tkGroup);
			allowedGroupUserService.addUser(groupDTO, tkUser);
		} catch (SQLException e) {
			messageService.unicastMessage("GROUP NOT FOUND");
			e.printStackTrace();
		}
	}
	
	private void open(String...args) {
		String tkGroup = args[2].toString(); 
		
		try {
			GroupDTO groupDTO = groupService.findGroup(tkGroup);
			messageService.unicastMessage("-------GROUP: " + groupDTO.getName() + " | " + groupDTO.getToken() + " -------");
		} catch (Exception e) {
			messageService.unicastMessage("GROUP NOT FOUND");
			e.printStackTrace();
		}
	}

}
