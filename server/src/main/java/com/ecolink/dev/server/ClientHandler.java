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
	private String clientUsername;
    private String password;
    private ClientService clientService = new ClientService();
	public ClientHandler(Socket socket) {
		try {
			// Write - Char
			// Stream - Byte
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//			this.clientUsername = bufferedReader.readLine();
			
//		    startSession(bufferedReader, this.clientUsername);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");
			
		}catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
	}
	
    public void startSession(BufferedReader bufferedReader, String clientUsername) throws IOException{
        System.out.println(clientUsername);
        unicastMessage("password:");
        this.password = bufferedReader.readLine();
        User user = clientService.login(clientUsername, password);
        System.out.println(user.getName() + " LOGADO");
        unicastMessage("Welcome to EcolinkCLI, " + this.clientUsername);
        clientHandlers.add(this);
    }	


	public void unicastMessage(String messageToSend) {
		
			try {
				bufferedWriter.write(messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
			}
		
	}
	
	public void broadcastMessage(String messageToSend) {
		for (ClientHandler clientHandler: clientHandlers) {
			try {
				if(!clientHandler.clientUsername.equals(clientUsername)) {
					clientHandler.bufferedWriter.write(messageToSend);
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
		broadcastMessage("SERVER: " + clientUsername + " has left the chat!");
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
	
	@Override // Thread - code block paralelo
	public void run() {
		String messageFromClient;
		
		while(socket.isConnected()) {
			try {
				if(clientStatus()) {
					messageFromClient = bufferedReader.readLine();
					//Commands
					if(messageFromClient.equals("ping")) {
						System.out.println("Teste");
						unicastMessage("connected");
					}
					//Message Obj
//					System.out.println(messageFromClient);
					System.out.println(bufferedReader.readLine());
//					broadcastMessage(messageFromClient);
				}
				closeEverything(socket, bufferedReader, bufferedWriter);
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
				break;
			}
		}
		
	}
	
}