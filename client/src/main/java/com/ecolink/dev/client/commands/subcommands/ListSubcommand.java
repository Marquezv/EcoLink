package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "list",
	mixinStandardHelpOptions = true)
public class ListSubcommand implements Runnable {
	

	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	
	@Option(names = {"-o","--online"}, description = "Online")
	private boolean online;
	
	@Option(names = {"-u","--users"}, description = "User")
	private boolean user;
	
	@Option(names = {"-g","--group"}, description = "Group")
	private boolean group;
	
	@Override
	public void run() {
		if(user) {
			String sendListUser = "list users";
			parent.getClientService().sendString(sendListUser);
		}
		if(online) {
			String sendListUserOnline = "list online";
			parent.getClientService().sendString(sendListUserOnline);
		}
		if(group) {
			String sendListGroup = "list group";
			parent.getClientService().sendString(sendListGroup);
		}
	}
	
	
}
