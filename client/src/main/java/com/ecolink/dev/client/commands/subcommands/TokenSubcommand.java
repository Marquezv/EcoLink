package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

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
		if(genToken) {
			parent.getClientService().sendString("gtoken");
		}
		
 	}
}
