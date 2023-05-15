package com.ecolink.dev.server.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.PriorityQueue;
import java.util.Queue;

import com.ecolink.dev.server.client.cli.Console;
import com.ecolink.dev.server.domain.UserDTO;
import com.ecolink.dev.server.services.MessageService;
import com.ecolink.dev.server.services.MessageServiceImpl;
import com.ecolink.dev.server.utils.ListenerFactory;
import com.ecolink.dev.server.utils.ListenerFunction;

public class ClientHandler implements Runnable {

	public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	Queue<String> testStringsPQ = new PriorityQueue<>();
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	private UserDTO userDTO;
	private MessageService messageService;
	private Console console;

	public ClientHandler(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.messageService = new MessageServiceImpl(this);
			this.console = new Console();
			clientHandlers.add(this);
		} catch (Exception e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
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

	public Console getConsole() {
		return console;
	}

	public void setConsole(Console console) {
		this.console = console;
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
			while (socket.isConnected()) {
				messageFromClient = bufferedReader.readLine();
				String[] msgArray = messageFromClient.split("\\s");
				System.out.println(console.getState());
				if (msgArray[1].equals("login")) {
					ListenerFactory factory = new ListenerFactory();
					ListenerFunction function = factory.createStringFunction(this, msgArray);
					function.apply(msgArray);
				}
				else if(this.userDTO == null ) {
					messageService.unicastMessage("[SERVER] Make your login -> user --login -tk=<your_token> -p=<your_pass>");
				} 
				else {
					if (console.getState().getName().equals("ConsoleCommand")) {
						ListenerFactory factory = new ListenerFactory();
						ListenerFunction function = factory.createStringFunction(this, msgArray);
						function.apply(msgArray);
					}
					if (msgArray[0].equals("/coc")) {
						console.onCommand();
					}
					if (console.getState().getName().equals("ConsoleMessage")) {
						System.out.println(messageFromClient);
						console.setClientHandler(this);
						console.processInput(socket, this, messageFromClient);
					}
				}
			}
		} catch (Exception e) {
			if (this.userDTO != null) {
				messageService.broadcastMessage("SERVER: " + this.userDTO.getName() + " has left the chat!");
			} else {
				messageService.broadcastMessage("SERVER: " + this.socket.getInetAddress() + " has left the chat!");
			}
			clientHandlers.remove(this);
			closeEverything(socket, bufferedReader, bufferedWriter);
		}

	}

}