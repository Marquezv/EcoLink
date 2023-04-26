package com.ecolink.dev.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.repository.UserDao;
import com.ecolink.dev.server.services.UserService;
import com.ecolink.dev.server.services.UserServiceImpl;

public class ClientHandler implements Runnable{
	
	
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	private UserDTO userDTO;
    private UserService userService;
    
	public ClientHandler(Socket socket) {
		try {
			// Write - Char
			// Stream - Byte
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.userService = new UserServiceImpl(new UserDao());
		    startSession();
		    clientHandlers.add(this);
		}catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
	}
	
    public void startSession() throws Exception{
        unicastMessage("Conneted to EcoLink");
    }	
    

	public void unicastMessage(String messageToSend) {
			if(userDTO == null)  messageToSend = "User not found!";
			try {
				bufferedWriter.write("[SERVER] - " + messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
		
	}
	
	public void broadcastMessage(String messageToSend) {
		for (ClientHandler clientHandler: clientHandlers) {
			try {
				if(!clientHandler.userDTO.name.equals(userDTO.name)) {
					clientHandler.bufferedWriter.write("[SERVER]: " + messageToSend);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();
				}
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
		}
	}
	
	public void removeClientHandler() {
		clientHandlers.remove(this);
		broadcastMessage("SERVER: " + this.userDTO.getName() + " has left the chat!");
	}
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		removeClientHandler();
		try {
			bufferedReader.close();
			bufferedWriter.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
        }
	}
	
	public List<String> findAllUsersOnline() {
		List<String> users = new ArrayList<>();
		for(ClientHandler clientHandler : clientHandlers) {
			try {
				if(clientHandler.clientStatus()) {
					users.add("token:" + clientHandler.userDTO.getToken() + "|user:" + clientHandler.userDTO.getName() );
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return users;
	}
	
	public boolean clientStatus() throws IOException {
		if(userDTO == null || userDTO.equals(null)) {
			return false;
		}
		return true;
	}

	
	public void listener(String...args) throws Exception {
		if(args[0].toString() == "loged" || args[0].toString().equals("loged")) {
			String loged = "false";
			if(this.userDTO != null) loged = "true";
			System.out.println(loged);
			unicastMessage(loged);
		}
		if(args[0].toString() == "ping" || args[0].toString().equals("ping")) {
			boolean loged = false;
			if(this.userDTO != null) loged = true;
			unicastMessage("[CONFIG] Server UP \nLoged: " + loged);
		}
		if(args[0].toString() == "login" || args[0].toString().equals("login")) {
			String token = args[1].toString();
			String password = args[2].toString();
			try {
				userDTO = userService.login(token, password);
				unicastMessage("[LOGED] token: " + userDTO.getToken() + " user: " + userDTO.getName());
			} catch (Exception e) {
				unicastMessage("[ERROR] token: " + token + " NOT FOUND");
			}
			
		}
		if(args[0].toString() == "create-user" || args[0].toString().equals("create-user")) {
			String token = args[1].toString();
			String username = args[2].toString();
			String password = args[3].toString();
			UserDTO user = new UserDTO(token, username, password);
			userService.saveUser(user);
			unicastMessage("[Created] token: " + user.getToken() + " name: " + user.getName());
		}
		if(args[0].toString() == "update-user" || args[0].toString().equals("update-user")) {
			UserDTO user = userService.login(args[1], args[2]);
			user.setName(args[1]);
			user.setPassword(args[2]);
			System.out.println();
			userService.saveUser(user);
			unicastMessage("[Update] token: " + user.getToken() + " name: " + user.getName());
		}
		if(args[0].toString() == "gtoken" || args[0].toString().equals("gtoken")) {
			String token = userService.genUserToken();
			UserDTO user = new UserDTO(token, token, token);
			userService.saveUser(user);
			unicastMessage("Token: " + token + "\nUser: " + token + "\nPassword: " + token + "\nChange your data after login");
		}
	}	
	
	
	@Override // Thread - code block paralelo
	public void run() {
		String messageFromClient;
		try {
			while(socket.isConnected()) {
				messageFromClient = bufferedReader.readLine();
				String[] msgArray = messageFromClient.split("\\s");
				System.out.println(findAllUsersOnline());
				listener(msgArray);
				System.out.println(clientStatus());
//				broadcastMessage(messageFromClient);
				
			}	
		} catch (Exception e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
		
	}
	
}