package com.ecolink.dev.client.commands;

import java.io.IOException;
import java.net.Socket;

import com.ecolink.dev.client.commands.subcommands.ConfigSubcommand;
import com.ecolink.dev.client.commands.subcommands.SendMessageSubcommads;
import com.ecolink.dev.client.commands.subcommands.TokenSubcommand;
import com.ecolink.dev.client.commands.subcommands.UserSubcommand;
import com.ecolink.dev.client.domain.User;
import com.ecolink.dev.client.services.ClientService;
import com.ecolink.dev.client.services.ClientServiceImpl;

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
			UserSubcommand.class,
			TokenSubcommand.class,
			ConfigSubcommand.class})
public class CommandControl{
	@Spec CommandSpec spec;
	
	private Socket socket;
	private ClientService clientService;
	private User user;

	public CommandControl(Socket socket) {
		try {
			this.socket = socket;
			this.clientService = new ClientServiceImpl(socket);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}


