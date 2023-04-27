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
	
	@Option(names = {"--list"}, description = "List users in that group")
	private boolean login;
	
	@Override
	public void run() {
		
	}
	
}
