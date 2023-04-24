package com.ecolink.dev.client.commands.subcommands;

import java.io.IOException;

import com.ecolink.dev.client.commands.CommandControl;
import com.ecolink.dev.client.services.ClientService;

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
	
	@Option(names = {"-u", "--username"}, description = "Your Username", required = true)
	private String username;
	
	@Option(names = {"-p", "--password"}, description = "Your Password", required = true)
	private String password;
	
	private boolean checkCredentials() {
		if ((username != "" || username != null) && (password != "" || password != null)) {
			return true;
		}
		return false;
	}
		
	
	@Override
	public void run() {
		try {
			if(checkCredentials()) {
				if(update && token != "" || token != null) {
					String sendCreate = "update-user " + username + " " + password;
					new ClientService(parent.getSocket()).sendMessage(sendCreate);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
