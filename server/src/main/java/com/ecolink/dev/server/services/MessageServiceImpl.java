package com.ecolink.dev.server.services;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

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
		for(ClientHandler clients : clientHandler.getClientHandlers()) {
			try {
				UserDTO userDTO = clients.getUserDTO();
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
				String userToken = clientHandler.getUserDTO().getToken();
				if(!clients.getUserDTO().getToken().equals(userToken)) {
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

	@Override
	public void anyMessage(String messageToSend, List<ClientHandler> group) {
		for(ClientHandler clients : group) {
			try {
				String userToken = clientHandler.getUserDTO().getToken();
				if(!clients.getUserDTO().getToken().equals(userToken)) {
					clients.getBufferedWriter().write("[GROUP]: " + messageToSend);
					clients.getBufferedWriter().newLine();
					clients.getBufferedWriter().flush();
				}
			}catch (IOException e) {
				e.printStackTrace();
	        }
		}
	}
	
	
}	
