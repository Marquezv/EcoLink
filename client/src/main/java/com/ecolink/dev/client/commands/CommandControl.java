package com.ecolink.dev.client.commands;

import java.net.Socket;

import com.ecolink.dev.client.commands.subcommands.LoginSubcommand;
import com.ecolink.dev.client.commands.subcommands.SendMessageSubcommads;
import com.ecolink.dev.client.domain.User;

import lombok.Getter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Getter
@Command(name = "coc",
	version = "0.0.0v",
	description = "Send commands to EcoLink Server",
	mixinStandardHelpOptions = true,
	subcommands = {
			SendMessageSubcommads.class,
			LoginSubcommand.class})
public class CommandControl{
	@Spec CommandSpec spec;
	
	private Socket socket;
	
	private User user;

	public CommandControl(Socket socket) {
		super();
		this.socket = socket;
	}
	
	
}


