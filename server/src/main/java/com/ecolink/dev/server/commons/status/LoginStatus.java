package com.ecolink.dev.server.commons.status;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum LoginStatus {
	
	NONE("NONE"),
	PUBLIC("LOGED"),
	PRIVATE("OUT");
	
	private String description;

	public String getDescription() {
	        return description;
	}
	
}
