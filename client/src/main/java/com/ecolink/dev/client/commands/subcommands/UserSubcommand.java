package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "user",
	mixinStandardHelpOptions = true)
public class UserSubcommand implements Runnable{
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {"--login"}, description = "Login")
	private boolean login;
	
	@Option(names = {"--create"}, description = "Create User")
	private boolean create;

	@Option(names = {"-tk", "--token"}, description = "Your Token", required = true)
	private String token;
	
	@Option(names = {"-u", "--username"}, description = "Your Username")
	private String username;
	
	@Option(names = {"-p", "--password"}, description = "Your Password", required = true)
	private String password;
	
	private boolean checkCredentials() {
		if ((token != "" || token != null) && (password != "" || password != null)) {
			return true;
		}
		return false;
	}
	
	
	@Override
	public void run() {
		if(checkCredentials()) {
			if(login) {
				String sendLogin = "user login " + token + " " + password;
				parent.getClientService().sendString(sendLogin);
			}else if(create && username != "" || username != null) {
				String sendCreate = "user create-user" + " " + token + " " + username + " " + password;
				parent.getClientService().sendString(sendCreate);
			}else {
		        System.out.println("Please select either --login or --create");
			}
		}
		
 	}
}
