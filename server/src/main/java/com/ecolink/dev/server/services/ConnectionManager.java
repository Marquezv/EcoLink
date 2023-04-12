package com.ecolink.dev.server.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class ConnectionManager {
	
	
	private HashMap<String, String> connections = new HashMap<String, String>();
	
	private List<String> names;
	
	public void add(String name, String token) {
		connections.put(name, token);
		names.add(name);
	}
	
	
   // public List<Entry<String, String>> allConnections() {
	//	return connections.entrySet().stream().toList();
	//}
}
