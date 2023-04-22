package com.ecolink.dev.client.commands;

import com.ecolink.dev.client.commands.subcommands.SendMessageSubcommads;

import picocli.CommandLine.Command;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Spec;

@Command(name = "coc",
	version = "0.0.0v",
	description = "Send commands to EcoLink Server",
	mixinStandardHelpOptions = true,
	subcommands = {SendMessageSubcommads.class})
public class CommandControl{
	@Spec CommandSpec spec;
	 
}


