package com.ecolink.dev.server;

import java.net.ServerSocket;


public class App {
	
    public static void main( String[] args ) {
    	
    	int PORT = 7000;
    	
		try {
			// IP 192.168.15.8
			ServerSocket servidor = new ServerSocket(PORT);
			System.out.println("Server Started - PORT[" + PORT + "]");
			
			
			while(true) {
				
				
				connection(servidor);
				
			}
			
		} catch (Exception e) {
			System.out.println("ERROR - " + e.getMessage());
		}
    }
    
    
    private static void connection(ServerSocket server) {
    	int conn_counter = 0;
    	try {
			new ClientConnection(server.accept()).start();
			conn_counter ++;
		}
		catch (Exception e) {
			System.out.println("ERROR - " + e.getMessage());
		}
		
		System.out.println("Conexoes: " + conn_counter);
    }
}
