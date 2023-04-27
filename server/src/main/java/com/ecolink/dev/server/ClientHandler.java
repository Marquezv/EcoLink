package com.ecolink.dev.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
    // Filas de mensagens apos logar ler as mensagens na fila
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
			try {
				bufferedWriter.write("[SERVER] - " + messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
		
	}
	
	public void sendToTokens(String messageToSend, String tkUser) {
		for(ClientHandler clientHandler : clientHandlers) {
			try {
				System.out.println(tkUser);
				System.out.println(clientHandler.userDTO.getToken());
				System.out.println(clientHandler.userDTO.getToken().equals(tkUser));
				if(clientHandler.userDTO.getToken().equals(tkUser)) {
					bufferedWriter.write("["+ userDTO.getToken() + "|" + userDTO.getName() +"] - " + messageToSend);
					bufferedWriter.newLine();
					bufferedWriter.flush();
				}
			}catch (Exception e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
			
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
				unicastMessage("Client not logged");
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
	
	public String formatList(List<String> list) {
		return list.stream().map(item -> "\n* " + item)
				.collect(Collectors.joining("\n"));
	}
	
	public boolean clientStatus() throws IOException {
		if(userDTO == null || userDTO.equals(null)) {
			return false;
		}
		return true;
	}

	
	public void listener(String...args) throws Exception {
		if(args[0].toString() == "send-string" || args[0].toString().equals("send-string")) {
			System.out.println(String.join(" ", args));
			String global = args[1].toString();
			String [] substring = Arrays.copyOfRange(args, 3, args.length);
			String message = String.join(" ", substring);
			String token = args[2].toString();
			if(global == "true" || global.equals("true")) {
				broadcastMessage(message);
			}
			System.out.println("Message to: " + token);
			sendToTokens(message, token);
		}
		if(args[0].toString() == "loged" || args[0].toString().equals("loged")) {
			String loged = "false";
			if(this.userDTO != null) loged = "true";
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
			System.out.println("Update " );
			unicastMessage("Update");
			System.out.println(userDTO == null);
			if( userDTO != null) {
				userDTO.setName(args[1]);
				userDTO.setPassword(args[2]);
				userService.updateUser(userDTO);
				unicastMessage("[Update] token: " + userDTO.getToken() + " name: " + userDTO.getName());
			} else {
				System.out.println("User not loged");
				unicastMessage("User not loged");
			}
			
		}
		if(args[0].toString() == "gtoken" || args[0].toString().equals("gtoken")) {
			String token = userService.genUserToken();
			UserDTO user = new UserDTO(token, token, token);
			userService.saveUser(user);
			unicastMessage("Token: " + token + "\nUser: " + token + "\nPassword: " + token + "\nChange your data after login");
		}
		if(args[0].toString() == "list" || args[0].toString().equals("list")) {
//			if(args[1].toString() == "online" || args[1].toString().equals("online")) 
				
			List<String> users = findAllUsersOnline();
			unicastMessage(formatList(users));
		}
	}	
	
	
	@Override // Thread - code block paralelo
	public void run() {
		String messageFromClient;
		try {
			while(socket.isConnected()) {
				messageFromClient = bufferedReader.readLine();
				String[] msgArray = messageFromClient.split("\\s");
				listener(msgArray);
//				broadcastMessage(messageFromClient);
				
			}	
		} catch (Exception e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
		
	}
	
}