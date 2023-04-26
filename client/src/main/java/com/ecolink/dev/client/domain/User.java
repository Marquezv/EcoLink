package com.ecolink.dev.client.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private String name;
    private String token;
    private String password;
    
    public User(String name, String password) {
        this.name = name;
        this.token = "";
    }
    
    @Override
    public String toString() {
        return "token: " + token + " - user: " + name;
    }
}
