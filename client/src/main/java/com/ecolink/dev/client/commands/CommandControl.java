package com.ecolink.dev.client.commands;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "CommandControl",
mixinStandardHelpOptions = true,
version = "0.0.0v",
description = "CommandControl")
public class CommandControl implements Callable<Integer>{

	@Option(names = {"-pg", "--ping"}, description = "User name")
	String ping = "ping";
	
	public Integer call()  {
	
		return 0;
	}

}
