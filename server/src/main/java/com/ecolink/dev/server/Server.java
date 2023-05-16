package com.ecolink.dev.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.ecolink.dev.server.client.ClientHandler;

public class Server {

	private ServerSocket serverSocket;
	
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}
	
	public void startServer() {
		try {			
			while(!serverSocket.isClosed()) {
				Socket socket = serverSocket.accept();
				System.out.println("Client connected: " + socket.getInetAddress() + " Port: " + socket.getPort());
				ClientHandler clientHandler = new ClientHandler(socket);
				Thread thread = new Thread(clientHandler);
				thread.start();

			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	public void closeServerSocket() {
		try {
			if(serverSocket != null) {
				serverSocket.close();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

  public static void main( String[] args ) {

    	int PORT = 7000;

		try {
			// IP 192.168.15.8
				ServerSocket serverSocket = new ServerSocket(PORT);
				Server server = new Server(serverSocket);
				System.out.println("Server Started - PORT[" + PORT + "]");
				server.startServer();
				
			} catch (Exception e) {
				System.out.println("ERROR - " + e.getMessage());
			}
  }


}
