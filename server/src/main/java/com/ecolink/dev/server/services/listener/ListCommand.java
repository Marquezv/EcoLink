package com.ecolink.dev.server.services.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ecolink.dev.server.ClientHandler;
import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.repository.UserDao;
import com.ecolink.dev.server.services.MessageService;
import com.ecolink.dev.server.services.MessageServiceImpl;
import com.ecolink.dev.server.services.UserService;
import com.ecolink.dev.server.services.UserServiceImpl;
import com.ecolink.dev.server.utils.ListenerFunction;

public class ListCommand implements ListenerFunction{

	//String =[user] ... ... ... 
	private UserService userService;
	private MessageService messageService;
	
	public ListCommand(ClientHandler clientHandler) {
		super();
		this.messageService = new MessageServiceImpl(clientHandler);
		this.userService = new UserServiceImpl(new UserDao(), clientHandler);
	}

	@Override
	public void apply(String...args) {
		try {
			if(args[1].toString() == "online" || args[1].toString().equals("online")) {
				messageService.unicastMessage(formatList(userService.getAllUsersOnline()));
			}
			if(args[1].toString() == "users" || args[1].toString().equals("users")) {
				List<UserDTO> userList = userService.getAllUsers();
				messageService.unicastMessage(formatList(userList));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
		
	private String formatList(List<UserDTO> list) {
		List<String> listUserFormat = new ArrayList<String>();
		for(UserDTO userDTO : list) {
			listUserFormat.add("token:" + userDTO.getToken() + "|user:" + userDTO.getName() );
		}
		return listUserFormat.stream().map(item -> "\n* " + item)
				.collect(Collectors.joining("\n"));
	}
	
}
