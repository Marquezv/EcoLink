package com.ecolink.dev.client.commands;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import com.ecolink.dev.client.commands.subcommands.ConfigSubcommand;
import com.ecolink.dev.client.commands.subcommands.GroupSubcommand;
import com.ecolink.dev.client.commands.subcommands.ListSubcommand;
import com.ecolink.dev.client.commands.subcommands.SendMessageSubcommand;
import com.ecolink.dev.client.commands.subcommands.TokenSubcommand;
import com.ecolink.dev.client.commands.subcommands.UserSubcommand;
import com.ecolink.dev.client.domain.Message;
import com.ecolink.dev.client.services.ClientService;
import com.ecolink.dev.client.services.ClientServiceImpl;

import lombok.Getter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Getter
@Command(name = "/coc",
	version = "0.0.0v",
	description = "Send commands to EcoLink Server",
	mixinStandardHelpOptions = true,
	subcommands = {
			SendMessageSubcommand.class,
			UserSubcommand.class,
			TokenSubcommand.class,
			ConfigSubcommand.class,
			ListSubcommand.class,
			GroupSubcommand.class})
public class CommandControl {
	@Spec CommandSpec spec;
	
	private Socket socket;
	private ClientService clientService;
	private List<Message> historyComands;
	
	public CommandControl(Socket socket) {
		try {
			this.socket = socket;
			this.clientService = new ClientServiceImpl(socket);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}


