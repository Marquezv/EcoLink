package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;


@Command(name = "create",
mixinStandardHelpOptions = true)
public class CreateSubcommand implements Runnable{

@ParentCommand
private CommandControl parent;

@Spec
private CommandLine.Model.CommandSpec spec;
	
	

	@Option(names = {"-", "--password"}, description = "Your password", required = true)
	private String password;
	
	@Option(names = {"-u", "--username"}, description = "Your username", required = true)
	private String username;
	
	private boolean checkLogin() {
		if ((username != "" || username != null) && (password != "" || password != null)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		if(checkLogin()) {
			String sendLogin = "login " + username + " " + password;
			parent.getClientService().sendString(sendLogin);
		}
	
	}
}
