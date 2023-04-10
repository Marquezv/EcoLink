package com.ecolink.dev.client.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class Message implements Serializable{

private static final long serialVersionUID = 1L;
	
	private String sender_id;
	private String receiver_id;
	private String message_content;
	private LocalDateTime timestamp;
//	private MessageStatus status;

	
	public Message(String sender_id, String receiver_id, String message_content, LocalDateTime timestamp) {
		super();
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
		this.message_content = message_content;
		this.timestamp = timestamp;
//		TokenServer();
	}
	
//	private void TokenServer () {
//		this.messageToken = UUID.randomUUID().toString().substring(0, 5);
//	}

}
