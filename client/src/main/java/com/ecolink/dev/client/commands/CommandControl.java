package com.ecolink.dev.client.commands;

import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "CommandControl",
mixinStandardHelpOptions = true,
version = "0.0.0v",
description = "CommandControl")
public class CommandControl implements Callable<Integer>{

	@Option(names = {"-u", "--user"}, description = "User name")
	String user;
	
	@Option(names = {"-p", "--password"}, description = "Passphrase", interactive = true)
	char[] password;
	
	public Integer call() throws Exception {
		byte[] bytees = new byte[password.length];
		
		System.out.println("user:" + user +"%npassword: " + password);
	
		return 0;
	}

}
