package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "config",
	mixinStandardHelpOptions = true)
public class ConfigSubcommand implements Runnable{
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {"--update"}, description = "Uptade User")
	private boolean update;
	
	@Option(names = {"-tk", "--token"}, description = "Your Token")
	private String token;
	
	@Option(names = {"-u", "--username"}, description = "Your New Username")
	private String username;
	
	@Option(names = {"-p", "--password"}, description = "Your New Password")
	private String password;
	
	@Option(names = {"ping"}, description = "Ping Server")
	private boolean ping;
	
	private boolean checkCredentials() {
		if ((username != "" || username != null) && (password != "" || password != null) && (token != "" || token != null)) {
			return true;
		}
		return false;
	}
		
	@Override
	public void run() {

		if(checkCredentials()) {
			String sendCreate = "user update-user " + username + " " + password;
			parent.getClientService().sendString(sendCreate);
		}
		
	}
	
}
