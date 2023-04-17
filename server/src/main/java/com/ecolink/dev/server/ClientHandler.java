package com.ecolink.dev.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.ecolink.dev.server.domain.User;
import com.ecolink.dev.server.services.ClientService;

public class ClientHandler implements Runnable{

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
	private Socket socket;
	private ObjectOutputStream out;
	private ObjectInputStream in;
	private User user;
    private ClientService clientService = new ClientService();
	public ClientHandler(Socket socket) {
		try {
			// Write - Char
			// Stream - Byte
			this.socket = socket;
			this.out = new ObjectOutputStream(socket.getOutputStream());
			this.in = new ObjectInputStream(socket.getInputStream());
			String clientUsername = "teste";
		    startSession(in, clientUsername);
            clientHandlers.add(this);
            broadcastMessage("SERVER: " + clientUsername + " has entered the chat");
	        System.out.println(userList());
        }catch (IOException | ClassNotFoundException e) {
            closeEverything(socket, in, out);
        }
	}

  public User startSession(ObjectInputStream in, String clientUsername) throws IOException, ClassNotFoundException{
//        String password = in.readObject().toString();
//        this.user = clientService.login(clientUsername, password);
//        if(this.user != null) {
//        	unicastMessage("Welcome to EcolinkCLI, " + this.user.name);
//        }else {
//         	unicastMessage("User not found!");
//        	socket.close();
//        }
	  	System.out.println((User) in.readObject());
        this.user = new User("vini", "o");
        return user;
    }

	public void unicastMessage(String messageToSend) {

			try {
				out.writeObject(messageToSend);
				out.flush();
			} catch (IOException e) {
				closeEverything(socket, in, out);
			}

	}

	public void broadcastMessage(String messageToSend) {
		String msg = "[" + this.user.name +  "]:" + messageToSend;
        for (ClientHandler clientHandler: clientHandlers) {
			try {
				if(!clientHandler.user.name.equals(this.user.name) && messageToSend != null) {
					clientHandler.out.writeObject(msg);
					clientHandler.out.flush();
				}
			} catch (IOException e) {
				closeEverything(socket, in, out);
			}
		}
	}

	public void removeClientHandler() {
		clientHandlers.remove(this);
		broadcastMessage("SERVER: " + this.user.name + " has left the chat!");
	}

	public void closeEverything(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
		try {
			if(out != null) {
				out.close();
			}
			if(in != null) {
				in.close();
			}
			if(socket != null) {
				socket.close();
			}
				
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

  public List<String> userList(){
        List<String> result = new ArrayList<String>();
        for(ClientHandler handler : clientHandlers){
            result.add(handler.user.name);
        }
        return result;
    }

	@Override // Thread - code block paralelo
	public void run() {
		String messageFromClient;

		while(socket.isConnected()) {
			try {
				User u = (User) in.readObject();
				//Message Obj
//				broadcastMessage(messageFromClient);
			} catch (IOException | ClassNotFoundException e) {
				closeEverything(socket, in, out);
				break;
			}
		}

	}

}
