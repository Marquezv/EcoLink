package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "login",
	mixinStandardHelpOptions = true)
public class LoginSubcommand implements Runnable{
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {"-u", "--username"}, description = "Your username", required = true)
	private String username;
	
	@Option(names = {"-p", "--password"}, description = "Your password", required = true)
	private String password;
	
	@Override
	public void run() {
//		if()
		
 	}
}
