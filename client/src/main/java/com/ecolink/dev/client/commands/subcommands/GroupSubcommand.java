package com.ecolink.dev.client.commands.subcommands;

import java.io.IOException;

import com.ecolink.dev.client.commands.CommandControl;
import com.ecolink.dev.client.services.ClientService;

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
	private Integer userLimit;

	@Override
	public void run() {
		System.out.println("Running subcommand");
		System.out.println("Group Name: " + groupName);
		System.out.println("Limit: " + userLimit);
			try {
				if(groupName != null && userLimit != 0) {
					new ClientService(parent.getSocket()).sendMessage("create-group " + groupName + userLimit);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
}
