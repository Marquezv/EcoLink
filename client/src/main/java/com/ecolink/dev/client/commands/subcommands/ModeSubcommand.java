package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "mode",
	mixinStandardHelpOptions = true)
public class ModeSubcommand implements Runnable {
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	
	@Option(names = {"-tkGroup", "--tokenGroup"}, description = "Token Group")
	private String tkGroup;
	
	@Option(names = {"-tkUser", "--tokenUser"}, description = "Token User")
	private String tkUser;
	
	@Option(names = {"--list"}, description = "List users in that group")
	private boolean list;
	
	@Option(names = {"close"}, description = "Close conversation")
	private boolean close;
	
	@Option(names = {"open"}, description = "Join in conversation")
	private boolean open;
	
	@Override
	public void run() {
		if(open && tkGroup != null) {
			String sendOpenGroup = "group open " + tkGroup;
			parent.getClientService().sendString(sendOpenGroup);
		}
		
	}
	
}
