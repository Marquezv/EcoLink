package com.ecolink.dev.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

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
//		    startSession(bufferedReader, this.clientUsername);
			System.out.println("Testing save...");
	    	System.out.println(this.userService.getAllUsers());
	    	System.out.println("Ok");
		}catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
	}
	
    public void startSession(String token, String password) throws Exception{
        UserDTO userDTO = userService.login(token, password);
        System.out.println(userDTO.getName() + " LOGADO");
        unicastMessage("Welcome to EcolinkCLI, " + userDTO.getName());
        clientHandlers.add(this);
    }	
    

	public void unicastMessage(String messageToSend) {
		
			try {
				bufferedWriter.write("[SERVER]: "+ messageToSend);
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
		broadcastMessage("SERVER: " + this.userDTO.name + " has left the chat!");
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
	
	public boolean clientStatus() throws IOException {
		if(bufferedReader.read() == -1) {
			return true;
		}
		return false;
	}

	
	public void listener(String...args) throws Exception {
		if(args[0].toString() == "login" || args[0].toString().equals("login")) {
			String username = args[1].toString();
			String password = args[2].toString();
			startSession(username, password);
		}
		if(args[0].toString() == "create-user" || args[0].toString().equals("login")) {
			String username = args[1].toString();
			String password = args[2].toString();
			startSession(username, password);
		}
		if(args[0].toString() == "create-group" || args[0].toString().equals("create-group")) {
			String username = args[1].toString();
			String password = args[2].toString();
			startSession(username, password);
		}
	}	
	
	@Override // Thread - code block paralelo
	public void run() {
		String messageFromClient;
		try {
			while(socket.isConnected()) {
				//Commad Reciver
				//Commad Reciver
				messageFromClient = bufferedReader.readLine();
				String[] msgArray = messageFromClient.split("\\s");
				System.out.println(msgArray);
				System.out.println(msgArray.length);
				listener(msgArray);
//				System.out.println(clientStatus());
//				broadcastMessage(messageFromClient);
				
			}	
		} catch (Exception e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
		
	}
	
}