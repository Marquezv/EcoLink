package com.ecolink.dev.client.commands.subcommands;

import java.io.IOException;

import com.ecolink.dev.client.commands.CommandControl;
import com.ecolink.dev.client.services.ClientService;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "token",
	mixinStandardHelpOptions = true)
public class TokenSubcommand implements Runnable{
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {"-g", "--generate"}, description = "Generate token")
	private boolean genToken;

	@Override
	public void run() {
		try {
			if(genToken) {
				new ClientService(parent.getSocket()).sendMessage("gtoken");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
 	}
}
