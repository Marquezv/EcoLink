package com.ecolink.dev.client.commands;

import java.io.BufferedReader;
import java.io.BufferedWriter;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "coc",
	version = "0.0.0v",
	description = "Send commands to EcoLink Server",
	mixinStandardHelpOptions = true)
public class CommandControl implements Runnable{
	
	@Option(names = {"-g", "global"}, description = "Global communiation")
	boolean global;
	
	@Parameters(paramLabel = "message", description = "Message to send")
	String message;
	
	public static void main(String[] args) {
		System.out.println(args);
		int exitCode = new CommandLine(new CommandControl()).execute(args);
	}

	@Override
	public void run() {
		
		// Criar Strategy para formatar e enviar Strings
		if(global) {
			System.out.println(message);
		}
	}

}
