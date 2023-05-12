package com.ecolink.dev.server.services.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ecolink.dev.server.client.ClientHandler;
import com.ecolink.dev.server.repository.GroupDao;
import com.ecolink.dev.server.repository.UserDao;
import com.ecolink.dev.server.services.GroupService;
import com.ecolink.dev.server.services.GroupServiceImpl;
import com.ecolink.dev.server.services.MessageService;
import com.ecolink.dev.server.services.MessageServiceImpl;
import com.ecolink.dev.server.services.UserService;
import com.ecolink.dev.server.services.UserServiceImpl;
import com.ecolink.dev.server.utils.ListenerFunction;

public class ListCommand implements ListenerFunction{

	//String =[list] ... ... ... 
	private UserService userService;
	private GroupService groupService;
	private MessageService messageService;
	
	public ListCommand(ClientHandler clientHandler) {
		super();
		this.messageService = new MessageServiceImpl(clientHandler);
		this.groupService = new GroupServiceImpl(new GroupDao(), clientHandler);
		this.userService = new UserServiceImpl(new UserDao(), clientHandler);
	}

	@Override
	public void apply(String...args) {
		try {
			if(args[1].toString() == "online" || args[1].toString().equals("online")) {
				String formattedList = formatList(userService.getAllUsersOnline(), userDTO -> "token:" + 
						userDTO.getToken() + "|user:" + userDTO.getName());
				messageService.unicastMessage(formattedList);
			}
			if(args[1].toString() == "users" || args[1].toString().equals("users")) {
				String formattedList = formatList(userService.getAllUsers(), userDTO -> "token:" + 
						userDTO.getToken() + "|user:" + userDTO.getName());
				messageService.unicastMessage(formattedList);
			}if(args[1].toString() == "group" || args[1].toString().equals("group")) {
				String formattedList = formatList(groupService.getAllGroups(), groupDTO -> "token:" + 
						groupDTO.getToken() + "|name:" + groupDTO.getName());
				messageService.unicastMessage(formattedList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private <T> String formatList(List<T> list, Function<T, String> formatFunction) {
	    List<String> listFormat = new ArrayList<String>();
	    for(T item : list) {
	        listFormat.add(formatFunction.apply(item));
	    }
	    return listFormat.stream().map(item -> "\n* " + item)
	        .collect(Collectors.joining("\n"));
	}
	
}
