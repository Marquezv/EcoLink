package com.ecolink.dev.server.commons.status;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum MessageStatus {
	
	NONE("NONE"),
	PUBLIC("SENT"),
	PRIVATE("DELIVERED");
	
	private String description;

	public String getDescription() {
	        return description;
	}
	
}
