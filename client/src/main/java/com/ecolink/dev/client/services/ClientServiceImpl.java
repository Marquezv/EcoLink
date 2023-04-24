package com.ecolink.dev.client.services;

import java.util.List;

import com.ecolink.dev.client.domain.User;

public class ClientServiceImpl implements ClientService {

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

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<User> findOnline() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
