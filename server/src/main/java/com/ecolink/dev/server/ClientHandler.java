package com.ecolink.dev.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.enums.State;
import com.ecolink.dev.server.services.MessageService;
import com.ecolink.dev.server.services.MessageServiceImpl;
import com.ecolink.dev.server.utils.ListenerFactory;
import com.ecolink.dev.server.utils.ListenerFunction;

public class ClientHandler implements Runnable{
	
	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private UserDTO userDTO;
	private MessageService messageService;
	private State state = State.NONE;
	
    // Filas de mensagens apos logar ler as mensagens na fila
	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.messageService = new MessageServiceImpl(this);
		    clientHandlers.add(this);
		}catch (Exception e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
	}
	
	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public ArrayList<ClientHandler> getClientHandlers() {
		return clientHandlers;
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

	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			bufferedReader.close();
			bufferedWriter.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
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
				System.out.println(state);
				ListenerFactory factory = new ListenerFactory();
				ListenerFunction function = factory.createStringFunction(this, msgArray);
				function.apply(msgArray);
				
			}	
		} catch (Exception e) {
			messageService.broadcastMessage("SERVER: " + this.userDTO.getName() + " has left the chat!");
			clientHandlers.remove(this);
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
		
	}
	
}