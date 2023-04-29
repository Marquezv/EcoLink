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
import com.ecolink.dev.server.utils.ListenerFactory;
import com.ecolink.dev.server.utils.ListenerFunction;

public class ClientHandler implements Runnable{
	
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private UserDTO userDTO;

    // Filas de mensagens apos logar ler as mensagens na fila
	public ClientHandler(Socket socket) {
		try {
			// Write - Char
			// Stream - Byte
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		    clientHandlers.add(this);
		}catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
	}
	
	public BufferedWriter getBufferedWriter() {
		return bufferedWriter;
	}

	public UserDTO getUserDTO() {
		return userDTO;
	}

	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
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
	// CLIENT -> SERVER(READ) -> TO_CLIENT
	public void sendToTokens(String messageToSend, String tkUser) {
		for(ClientHandler clientHandler : clientHandlers) {
			try {
				if(clientHandler.userDTO.getToken().equals(tkUser)) {
					clientHandler.bufferedWriter.write("["+ userDTO.getToken() + "|" + userDTO.getName() +"] - " + messageToSend);
					clientHandler.bufferedWriter.newLine();
					clientHandler.bufferedWriter.flush();
				}
			}catch (Exception e) {
				unicastMessage("User not loged");
//				closeEverything(socket, bufferedReader, bufferedWriter);
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
			String global = args[1].toString();
			String [] substring = Arrays.copyOfRange(args, 3, args.length);
			String message = String.join(" ", substring);
			String token = args[2].toString();
			if(global == "true" || global.equals("true")) {
				broadcastMessage(message);
			}
			sendToTokens(message, token);
		}
		
		if(args[0].toString() == "list" || args[0].toString().equals("list")) {
			if(args[1].toString() == "online" || args[1].toString().equals("online")) {
				List<String> users = findAllUsersOnline();
				unicastMessage(formatList(users));
			}
			if(args[1].toString() == "users" || args[1].toString().equals("users")) {
				
			}
			
		}
		
	}	
	
	
	@Override // Thread - code block paralelo
	public void run() {
		String messageFromClient;
		try {
			while(socket.isConnected()) {
				messageFromClient = bufferedReader.readLine();
				String[] msgArray = messageFromClient.split("\\s");

				System.out.println(messageFromClient);
				
				ListenerFactory factory = new ListenerFactory();
				ListenerFunction function = factory.createStringFunction(this, msgArray);
				function.apply(msgArray);
//				listener(msgArray);
//				broadcastMessage(messageFromClient);
				
			}	
		} catch (Exception e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
		
	}
	
}