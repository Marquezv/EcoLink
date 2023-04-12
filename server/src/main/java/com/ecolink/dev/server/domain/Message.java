package com.ecolink.dev.server.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Message implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String sender_id;
	private String receiver_id;
	private String message_content;
	private LocalDateTime timestamp;
//	private MessageStatus status;
//	private boolean encryption;
	
}
