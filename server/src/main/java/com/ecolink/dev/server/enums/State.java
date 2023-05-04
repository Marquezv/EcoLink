package com.ecolink.dev.server.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum State {
	NONE("NONE"),
	GROUP("GROUP"),
	USER("USER");
	
	@Getter private String value;
	
}
