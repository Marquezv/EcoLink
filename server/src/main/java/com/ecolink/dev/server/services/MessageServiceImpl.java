package com.ecolink.dev.server.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.ecolink.dev.server.ClientHandler;

public class MessageServiceImpl implements MessageService{
	
	private Socket socket;
	private BufferedWriter bufferedWriter;
	private ClientHandler clientHandler;
	
	public MessageServiceImpl(ClientHandler clientHandler) {
		super();
		this.bufferedWriter = clientHandler.getBufferedWriter();
		this.clientHandler = clientHandler;
	}

	@Override
	public void unicastMessage(String messageToSend) {
		try {
			bufferedWriter.write("[SERVER] - " + messageToSend);
			bufferedWriter.newLine();
			bufferedWriter.flush();
		} catch (IOException e) {
			close();
		}
	}

	@Override
	public void sendToTokens(String messageToSend, String tkUser) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void broadcastMessage(String messageToSend, List<ClientHandler> group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void anyMessage(String messageToSend, List<String> group) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		try {
			bufferedWriter.close();
			socket.close();
			System.out.println("[Client] Disconected");
		} catch (IOException e) {
			e.printStackTrace();
        }
	}
	
	
}	
