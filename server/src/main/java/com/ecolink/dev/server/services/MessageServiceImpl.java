package com.ecolink.dev.server.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;

import com.ecolink.dev.server.client.ClientHandler;
import com.ecolink.dev.server.domain.UserDTO;

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
		System.out.println(clientHandler.getClientHandlers().size());
		for(ClientHandler clients : clientHandler.getClientHandlers()) {
			System.err.println(clients.getUserDTO().toString());
			UserDTO userDTO = clients.getUserDTO();
			try {
				if(userDTO.getToken().equals(tkUser)) {
					clients.getBufferedWriter().write("["+ clientHandler.getUserDTO().getToken() + "|" + clientHandler.getUserDTO().getName() +"] - " + messageToSend);
					clients.getBufferedWriter().newLine();
					clients.getBufferedWriter().flush();
				}
			}catch (Exception e) {
				unicastMessage("User not loged");
			}
			
		}
		
	}

	@Override
	public void broadcastMessage(String messageToSend) {
		for (ClientHandler clients: clientHandler.getClientHandlers()) {

			try {
				if(!clients.getUserDTO().getToken().equals(clientHandler.getUserDTO().getToken())) {
					clients.getBufferedWriter().write("[SERVER]: " + messageToSend);
					clients.getBufferedWriter().newLine();
					clients.getBufferedWriter().flush();
				}
			} catch (IOException e) {
				unicastMessage("Client not logged");
			}
		}		
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
