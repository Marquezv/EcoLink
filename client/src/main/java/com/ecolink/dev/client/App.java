package com.ecolink.dev.client;

import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

import com.ecolink.dev.client.domain.Client;


public class App 
{
    public static void main( String[] args )
    {
		try { 
			Socket cliente = new Socket("127.0.0.1", 7000);
				
			Scanner keyboard = new Scanner(System.in);
			Scanner entrada = new Scanner(cliente.getInputStream());
			
			PrintStream saida = new PrintStream(cliente.getOutputStream());
			
			String msg = "";
			
			Client cli = new Client("vini", "12");
			
			do {
				
				System.out.print("> ");
				msg = keyboard.nextLine();
				saida.println(msg);
				String resposta = entrada.nextLine();
				System.out.println(resposta);	
								
			} while(msg.length() != 0);
			
			cliente.close();
			keyboard.close();
		} catch (Exception e) {
			System.out.println("ERROR - " + e.getMessage());
		}
		
    }
   
}
