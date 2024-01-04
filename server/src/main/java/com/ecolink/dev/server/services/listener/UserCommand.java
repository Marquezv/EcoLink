package com.ecolink.dev.server.services.listener;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.ecolink.dev.server.client.ClientHandler;
import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.exception.ExException;
import com.ecolink.dev.server.exception.handler.ObservableExceptionHandler;
import com.ecolink.dev.server.repository.UserDao;
import com.ecolink.dev.server.services.MessageService;
import com.ecolink.dev.server.services.MessageServiceImpl;
import com.ecolink.dev.server.services.UserService;
import com.ecolink.dev.server.services.UserServiceImpl;
import com.ecolink.dev.server.utils.ListenerFunction;

public class UserCommand implements ListenerFunction{

	//String =[user] ... ... ... 
	private ClientHandler clientHandler;
	private UserService userService;
	private MessageService messageService;
	private ObservableExceptionHandler exception;
	
	public UserCommand(ClientHandler clientHandler) {
		super();
		this.clientHandler = clientHandler;
		this.messageService = new MessageServiceImpl(clientHandler);
		this.userService = new UserServiceImpl(new UserDao(), clientHandler);
		this.exception = new ObservableExceptionHandler();
        exception.addObserver(new ExException());
	}

	@Override
	public void apply(String...args) {
		
		if(args[1].toString() == "login" || args[1].toString().equals("login")) {
			login(args);
		}
		if(args[1].toString() == "create-user" || args[1].toString().equals("create-user")) {
			create(args);
		}
		if(args[1].toString() == "update-user" || args[1].toString().equals("update-user")) {
			update(args);
		}
		if(args[1].toString() == "gtoken" || args[1].toString().equals("gtoken")) {
			gtoken(args);
		}
		if(args[1].toString() == "list" || args[1].toString().equals("list")) {
			list(args);
		}
		
	}
	
	private void login(String...args) {
		String token = args[2].toString();
		String password = args[3].toString();
		try {
			this.clientHandler.setUserDTO(userService.login(token, password));
			String name = clientHandler.getUserDTO().getName();
			messageService.unicastMessage("[LOGED] token: " + token + " user: " + name);
		} catch (Exception e) {
            exception.handleException(e);
			messageService.unicastMessage("[ERROR] token: " + token + " NOT FOUND");
		}
	}
	
	
	private void create(String...args) {
		String token = args[2].toString();
		String username = args[3].toString();
		String password = args[4].toString();
		try {
			UserDTO user = new UserDTO(token, username, password);

			userService.saveUser(user);
			messageService.unicastMessage("[Created] token: " + user.getToken() + " name: " + user.getName());
		} catch (Exception e) {
			messageService.unicastMessage("[ERROR] Create user");
		}
	}
	
	private void update(String...args) {
		UserDTO userDTO = clientHandler.getUserDTO();
		if( userDTO != null) {
			userDTO.setName(args[2]);
			userDTO.setPassword(args[3]);
			System.out.println(userDTO);
			try {
				userService.updateUser(userDTO);
				messageService.unicastMessage("[Update] token: " + userDTO.getToken() + " name: " + userDTO.getName());
			} catch (Exception e) {
				System.out.println(e.getMessage());
				e.printStackTrace();
				messageService.unicastMessage("[ERROR] Update user");
			}
		} else {
			messageService.unicastMessage("User not loged");
		}
	}
	
	private void gtoken(String...args) {
		try {
			String token = userService.genUserToken();
			UserDTO user = new UserDTO(token, token, token);
			userService.saveUser(user);
			messageService.unicastMessage("Token: " + token + "\nUser: " + token + "\nPassword: " + token + "\nChange your data after login");
		} catch (Exception e) {
			messageService.unicastMessage("[ERROR] Generate Token user");
		}
	}
	
	private void list(String...args) {
		
			try {
				if(args[2].toString() == "online" || args[2].toString().equals("online")) {
					messageService.unicastMessage(formatList(userService.getAllUsersOnline()));
				}
				if(args[2].toString() == "users" || args[2].toString().equals("users")) {
					List<UserDTO> userList = userService.getAllUsers();
					messageService.unicastMessage(formatList(userList));
				}			
			} catch (Exception e) {
				// TODO Auto-generated catch block
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
