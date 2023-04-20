package com.ecolink.dev.client.commands.subcommands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command (name = "global")
public class GlobalSubcommand implements Runnable {
	
	@Option(names = {"-m", "--message"}, description = "Message")
	String message;
	
	@Override
	public void run() {
		System.out.println(message);
	}

}
