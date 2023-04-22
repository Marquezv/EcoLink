package com.ecolink.dev.client.commands.subcommands;

import java.io.IOException;

import com.ecolink.dev.client.commands.CommandControl;
import com.ecolink.dev.client.services.ClientService;

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
	
	@Option(names = {"-m", "--message"}, description = "Your message")
	private String message;
	
	@Option(names = {"-g", "--global"}, description = "Send to Global")
	private boolean global;
	
	@Override
	public void run() {
		System.out.println("Running subcommand");
		System.out.println("Message: " + message);
		System.out.println("Global: " + global);
			try {
				if(message != null) {
					new ClientService(parent.getSocket()).sendMessage(message);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

}