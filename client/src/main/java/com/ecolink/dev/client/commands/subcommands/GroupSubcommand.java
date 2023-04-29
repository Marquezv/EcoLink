package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "group",
	mixinStandardHelpOptions = true)
public class GroupSubcommand implements Runnable {
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {"--create"}, description = "Create group")
	private boolean create;
	
	@Option(names = {"-gn", "--group-name"}, description = "Group name")
	private String groupName;
	
	@Option(names = {"-p", "--password"}, description = "Password group")
	private String password;
	
	@Option(names = {"-ul", "--user-limit"}, description = "User limit")
	private String userLimit;


	@Option(names = {"--list"}, description = "List users in that group")
	private boolean list;
	
	@Option(names = {"--delete"}, description = "Delete group")
	private boolean delete;
	
	private boolean checkCredentials() {
		if ((groupName != "" || groupName != null)) {
			return true;
		}
		return false;
	}
	
	@Override
	public void run() {
		if(create && checkCredentials()) {
			String sendCreateGroup = "group create-group " + groupName + " " + password + " " + userLimit;
			parent.getClientService().sendString(sendCreateGroup);
		}
		if(list) {
			String sendListUsers = "list users";
			parent.getClientService().sendString(sendListUsers);
		}
	}
	
}
