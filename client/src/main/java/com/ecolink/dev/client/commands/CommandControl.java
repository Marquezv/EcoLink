package com.ecolink.dev.client.commands;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "CommandControl",
	version = "0.0.0v",
	description = "Send commands to EcoLink Server",
	mixinStandardHelpOptions = true)
public class CommandControl implements Runnable{
	
	
	
	@Option(names = {"-g", "--global"}, description = "Enter in global chat")
	boolean global;
	
	@Parameters(paramLabel = "<word>", description = "Message")
	private String[] word;
	
	public static void main(String[] args) {
		System.out.println(args);
		int exitCode = new CommandLine(new CommandControl()).execute(args);
		System.exit(exitCode);
	}
	
	@Override
	public void run() {
		if(global) {
			System.out.println(global);
		}
	}

}
