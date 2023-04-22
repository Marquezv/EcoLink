package com.ecolink.dev.client.services;

import java.io.BufferedWriter;
import java.util.List;

public interface ClientServiceImpl {
	
	public void sendMessage(String message);
	public List<String> listOnline();
	public List<String> listAll();
}

