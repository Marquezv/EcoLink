package com.ecolink.dev.client.commands.subcommands;

import java.io.IOException;

import com.ecolink.dev.client.commands.CommandControl;
import com.ecolink.dev.client.services.ClientService;

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
	
	@Option(names = {"-p", "--password"}, description = "Your password", required = true)
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
		try {
			if(checkLogin()) {
				System.out.println("login " + username + " " + password );
				new ClientService(parent.getSocket()).sendMessage("login " + username + " " + password );
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
 	}
}
