package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "send-message",
	mixinStandardHelpOptions = true)
public class SendMessageSubcommads implements Runnable{
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {"-m", "--message"}, description = "Your message", required=true)
	private String message;
	
	@Option(names = {"-g", "--global"}, description = "Send to Global")
	private boolean global;
	
	@Override
	public void run() {
		System.out.println("Running subcommand");
		System.out.println("Message: " + message);
		System.out.println("Global: " + global);
			if(message != null) {
				parent.getClientService().sendString(message);
			}
	}

}