package com.ecolink.dev.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;


public class Client {
	
	private Socket socket;
	private BufferedReader bufferedReader;
	private BufferedWriter bufferedWriter;
	
	public  Client(Socket socket) {
		try {
			this.socket = socket;
			this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 

			new CommandLine(new CommandControl(this.socket)).execute("-h");
		} catch (Exception e) {
			closeEverything(socket, bufferedReader, bufferedWriter);
		}
	}
	
	
	
	public void sendMessage() {
		try {
			Scanner scanner = new Scanner(System.in);
			while(socket.isConnected()) {
				System.out.print(">");
				String messageToSend = scanner.nextLine();	
				// Commands
				new CommandLine(new CommandControl(this.socket)).execute(messageToSend.split(" "));
			}
			scanner.close();
		} catch (Exception e) {
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
		System.out.println("Welcome to EcoLinkCLI");
		Socket socket = new Socket("localhost", 7000);
		Client client = new Client(socket);
		client.listenForMessage();
		client.sendMessage();
		
	}
	
}






















