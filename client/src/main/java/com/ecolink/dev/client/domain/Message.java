package com.ecolink.dev.client.domain;

import java.io.Serializable;
// import java.time.LocalDateTime;
import java.time.*;
import java.util.UUID;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class Message implements Serializable{

    private static final long serialVersionUID = 1L;
	
    private String messageToken;
	private String sender_id;
	private String receiver_id;
	private String message_content;
	private LocalDateTime timestamp;
//	private MessageStatus status;

	
	public Message(String sender_id, String receiver_id, String message_content) {
		super();
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
		this.message_content = message_content;
		this.timestamp = LocalDateTime.now();
		TokenServer();
	}
	
	private void TokenServer () {
		this.messageToken = UUID.randomUUID().toString().substring(0, 5);
	}

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Message{");
        sb.append("sender_id = ").append(sender_id);
        sb.append(", receiver_id = ").append(receiver_id);
        sb.append(", message_content = ").append(message_content);
        sb.append(", timestamp = ").append(timestamp);
        return sb.append("}").toString();
    }

}
