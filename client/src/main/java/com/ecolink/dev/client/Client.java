package com.ecolink.dev.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;


public class Client 
{
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;

	private String username;
	
	public  Client(Socket socket, String username) {
		try {
			this.socket = socket;
			this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			this.username = username;
		} catch (IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	
//	public boolean serverStatus(Socket socket) {
//		
//	}
	
	
	public void sendMessage() {
		try {
			bufferedWriter.write(username);
			bufferedWriter.newLine();
			bufferedWriter.flush();
			System.out.print(">");
			Scanner scanner = new Scanner(System.in);
			while(socket.isConnected()) {
				System.out.print(">");
				String messageToSend = scanner.nextLine();
				// Commands
				new CommandLine(new CommandControl()).execute(messageToSend);
				bufferedWriter.write(messageToSend);
				bufferedWriter.newLine();
				bufferedWriter.flush();
			}
		} catch (IOException e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	
	public void listenForMessage() {
		
		new Thread(new Runnable(){
			@Override
			public void run() {

				String msgFromGroupChat;
				while(socket.isConnected()) {
					try {
						msgFromGroupChat = bufferedReader.readLine();
						
						if(msgFromGroupChat != null) System.out.println(msgFromGroupChat);
						
					}catch (IOException e) {
						closeEverything(socket, bufferedReader, bufferedWriter);
					}
				}
			}
		}).start();
	}
	
	
	
	public void closeEverything(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
		try {
			if(bufferedReader != null) {
				bufferedReader.close();
			}
			if(bufferedWriter != null) {
				bufferedWriter.close();
			}
			if(socket != null) {
				socket.close();
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Closed");
	}
	
	public static void main(String[] args) throws IOException {
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter your username for the group chat: ");
		String username = scanner.nextLine();
		Socket socket = new Socket("localhost", 7000);
		Client client = new Client(socket, username);
		client.listenForMessage();
		client.sendMessage();
		
	}
	
}






















