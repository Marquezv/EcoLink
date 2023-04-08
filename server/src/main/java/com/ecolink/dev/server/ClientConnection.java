package com.ecolink.dev.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientConnection extends Thread{
	
	Socket client;
	
	ClientConnection(Socket cli) {
		this.client = cli;
	}
	
	@Override  
	public void run() {
	
		System.out.println("Cliente conectado com thread (" + this.getId() + ") : " + client.getInetAddress());
		Scanner keyboard = new Scanner(System.in);
		connection(keyboard);
		
		
	}
	
	private void chatServer(Scanner chegada, Scanner keyboard, PrintStream saida, String clientStr) {
		String msgChegadaCliente = chegada.nextLine();
		System.out.println(clientStr + "-> "+ msgChegadaCliente );
		String msgResposta = keyboard.nextLine();
		saida.println(msgResposta);
	}
	
	
	private void connection(Scanner keyboard) {
		try {			
			Scanner chegada = new Scanner(client.getInputStream());
			PrintStream saida = new PrintStream(client.getOutputStream());
			
			InputStreamReader fluxoDados = new InputStreamReader(client.getInputStream());
			BufferedReader dado = new BufferedReader(fluxoDados);
//			System.out.println(dado.readLine());
			String clientStr = client.getInetAddress().
                    getHostAddress() + ":"+ client.getPort();
			
			System.out.println("Cliente conectado do IP "+ clientStr);
			
			while(chegada.hasNextLine()) {
				chatServer(chegada, keyboard, saida, clientStr);
			}
			if (client.getKeepAlive()) {
				System.out.println("Cliente finalizado: " + client.getInetAddress() + " da thread (" + this.getId() + ")");
				client.close();
			}
			
			
			
		}
		 catch (Exception e) {
			System.out.println("Ocorreu um erro!");
		}
	}
	
}
