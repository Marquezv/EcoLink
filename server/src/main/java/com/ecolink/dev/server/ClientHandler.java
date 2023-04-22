package com.ecolink.dev.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.ecolink.dev.server.domain.User;
import com.ecolink.dev.server.services.ClientService;

public class ClientHandler implements Runnable{
	
	
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
    private User user;
    private ClientService clientService = new ClientService();
	public ClientHandler(Socket socket) {
		try {
			// Write - Char
			// Stream - Byte
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//		    startSession(bufferedReader, this.clientUsername);
			
		}catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
	}
	
    public void startSession(String username, String password) throws IOException{
        user = clientService.login(username, password);
        System.out.println(user.getName() + " LOGADO");
        unicastMessage("Welcome to EcolinkCLI, " + username);
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
				if(!clientHandler.user.name.equals(user.name)) {
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
		broadcastMessage("SERVER: " + this.user.name + " has left the chat!");
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

	
	public void listener(String...args) throws IOException {
		if(args[0].toString() == "login" || args[0].toString().equals("login")) {
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
		} catch (IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
		
	}
	
}