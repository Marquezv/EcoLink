package com.ecolink.dev.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
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
			String clientUsername = bufferedReader.readLine();
		    startSession(bufferedReader, clientUsername);
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");	
	        System.out.println(userList());
        }catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
	}
	
    public User startSession(BufferedReader bufferedReader, String clientUsername) throws IOException{
        unicastMessage("password:");
        String password = bufferedReader.readLine();
        this.user = clientService.login(clientUsername, password);
        if(this.user != null) {
        	unicastMessage("Welcome to EcolinkCLI, " + this.user.getName());
        }else {
         	unicastMessage("User not found!");
        	socket.close();
        }
        return this.user;
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
		String msg = "[" + this.user.name +  "]:" + messageToSend;
        for (ClientHandler clientHandler: clientHandlers) {
			try {
				if(!clientHandler.user.name.equals(this.user.getName()) && messageToSend != null) {
					clientHandler.bufferedWriter.write(msg);
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
			if(bufferedReader != null) bufferedReader.close(); 
			if(bufferedWriter != null) bufferedWriter.close();
			if(socket != null )socket.close();
		} catch (IOException e) {
			e.printStackTrace();
        }
	}
    
    public List<String> userList(){
        List<String> result = new ArrayList<String>();
        for(ClientHandler handler : clientHandlers){
            result.add(handler.user.getName());
        }
        return result;
    }

	@Override // Thread - code block paralelo
	public void run() {
		String messageFromClient;
		
		while(socket.isConnected()) {
			try {
				messageFromClient = bufferedReader.readLine();
				//Message Obj
				broadcastMessage(messageFromClient);
			} catch (IOException e) {
				closeEverything(socket, bufferedReader, bufferedWriter);
				break;
			}
		}
		
	}
	
}
