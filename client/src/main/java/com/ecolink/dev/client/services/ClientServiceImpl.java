package com.ecolink.dev.client.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientServiceImpl implements ClientService {
	
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;

	public ClientServiceImpl(Socket socket) throws IOException {
		this.socket = socket;
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())); 
		this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
	}
	
	@Override
	public void sendString(String message) {
		try {

			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	
}
