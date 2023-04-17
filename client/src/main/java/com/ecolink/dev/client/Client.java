package com.ecolink.dev.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import com.ecolink.dev.client.domain.User;


public class Client 
{
	
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private User username;
	
	public  Client(Socket socket, User user) {
		try {
			this.socket = socket;
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
			this.username = user;
			
		} catch (IOException e) {
			closeEverything(socket, in, out);
		}
	}
	
	public void sendMessage() {
		try {
			out.writeObject(username.toString());
			out.flush();
			
			Scanner scanner = new Scanner(System.in);
			while(socket.isConnected()) {
				String messageToSend = scanner.nextLine();
				
//				Message msg = new Message(username, "reciver_id", messageToSend, null);
	
				out.writeObject(messageToSend);
				out.flush();
			}
		} catch (IOException e) {
			closeEverything(socket, in, out);
		}
	}
	
	
	public void listenForMessage() {
		
		new Thread(new Runnable(){
			@Override
			public void run() {

				String msgFromGroupChat;
				while(socket.isConnected()) {
					try {
						msgFromGroupChat = in.readObject().toString();
						
						if(msgFromGroupChat != null) System.out.println(msgFromGroupChat);
						
					}catch (IOException | ClassNotFoundException e) {
						closeEverything(socket, in, out);
					}
				}
			}
		}).start();
	}
	
	
	
	public void closeEverything(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
		try {
			if(out != null) {
				out.close();
			}
			if(in != null) {
				in.close();
			}
			if(socket != null) {
				socket.close();
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static User login(Scanner scanner) {
		System.out.println("username: ");
		String username = scanner.nextLine();
		System.out.println("password:");
	    String password = scanner.nextLine();
	    User user = new User(username, password);
	    return user;
	}
	
	public static void main(String[] args) throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		Socket socket = new Socket("localhost", 7000);
		Client client = new Client(socket, login(scanner));
		client.listenForMessage();
		client.sendMessage();
		scanner.close();
	}
	
}


















