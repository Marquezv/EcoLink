package com.ecolink.dev.client.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientService implements ClientServiceImpl{
	
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	
	public ClientService(Socket socket) throws IOException {
		super();
		this.socket = socket;
		this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	@Override
	public void sendMessage(String message) {
		try {
			bufferedWriter.write(message);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<String> listOnline() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> listAll() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
