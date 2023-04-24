package com.ecolink.dev.client.services;

import java.net.Socket;
import java.util.List;

import com.ecolink.dev.client.domain.User;

public interface ClientService {
	
	private Socket socket;
	
	void sendString(String message);
	List<User> findAll();
	List<User> findOnline();
	
}
