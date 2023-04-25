package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "group",
	mixinStandardHelpOptions = true)
public class GroupSubcommand implements Runnable{
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {"-crt", "--create"}, description = "Group Name", required=true)
	private String groupName;
	
	@Option(names = {"-ul", "--userLimit"}, description = "Limit of users")
	private Integer userLimit = 0;

	@Override
	public void run() {
		System.out.println("Running subcommand");
		System.out.println("Group Name: " + groupName);
		System.out.println("Limit: " + userLimit);
			if(groupName != null && userLimit != 0) {
				parent.getClientService().sendString(groupName);
			}
	}
	
}
