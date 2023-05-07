package com.ecolink.dev.client.chat;

import java.net.Socket;

import com.ecolink.dev.client.commands.subcommands.GroupSubcommand;

import picocli.CommandLine;

public class ChatUser implements ChatState {
	
	private String name = "ChatUser";
	private Chat chat;
	
	public ChatUser(Chat chat) {
		super();
		this.chat = chat;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public void onCommand() {
		this.chat.setState(new ChatCommand(this.chat));
	}

	@Override
	public void onGroup() {
		this.chat.setState(new ChatGroup(this.chat));
	}

	@Override
	public void onUser() {
		System.out.println("[MODE - USER]");
	}

	@Override
	public void onGlobal() {
		this.chat.setState(new ChatGlobal(this.chat));
	}

	@Override
	public void processInput(Socket socket, String[] args) {
		if (args.length > 0 && args[0].equals("/g")) {
            try {
                GroupSubcommand groupCommand = new GroupSubcommand();
                CommandLine commandLine = new CommandLine(groupCommand);
                commandLine.execute(args);
            } catch (Exception e) {
                System.out.println("Failed to execute group command: " + e.getMessage());
            }
        } else {
            System.out.println("Invalid command for group mode.");
        }
		
	}

}
