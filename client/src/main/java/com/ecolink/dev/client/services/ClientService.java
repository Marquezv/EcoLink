package com.ecolink.dev.client.services;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.ecolink.dev.client.Client;

public class ClientService implements ClientServiceImpl{
	
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private BufferedReader bufferedReader;
	
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

	public ClientService(Socket socket, BufferedWriter bufferedWriter, BufferedReader bufferedReader) {
		super();
		this.socket = socket;
		this.bufferedWriter = bufferedWriter;
		this.bufferedReader = bufferedReader;
	}

	
	
	
}
