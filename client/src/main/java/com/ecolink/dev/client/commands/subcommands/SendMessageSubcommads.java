package com.ecolink.dev.client.commands.subcommands;

import com.ecolink.dev.client.commands.CommandControl;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;
import picocli.CommandLine.Spec;

@Command(name = "send-message",
	mixinStandardHelpOptions = true)
public class SendMessageSubcommads implements Runnable{
	
	@ParentCommand
	private CommandControl parent;
	
	@Spec
	private CommandLine.Model.CommandSpec spec;
	//Pensar em espaco
	@Option(names = {"-m", "--message"}, description = "Your message", required=true)
	private String message;
	
	@Option(names = {"-g", "--global"}, description = "Send to Global")
	private boolean global;
	
	@Option(names = {"-tk", "--token-user"}, description = "Token to send message")
	String tkUser;
	
	@Option(names = {"-gp", "--group"}, description = "Send to group")
	String tkGroup;
	
	@Override
	public void run() {
		System.out.println("Message: " + message);
		System.out.println("Global: " + global);
		System.out.println("tkUser: " + tkUser);
			if(message != null) {
				String msg = "send-string " + global + " "+ tkUser + " "+ message;
				System.out.println(msg);
				parent.getClientService().sendString(msg);
			}
	}

}