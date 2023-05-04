package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "-",
mixinStandardHelpOptions = true)
public class ChatSubcommand implements Runnable{
	

	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {":"}, description = "Send message in location")
	String messageLocation;
	
	
	@Override
	public void run() {
		if(messageLocation != null) {
		}
	}
	

}
