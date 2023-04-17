package com.ecolink.dev.client.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User implements Serializable {
	
    private static final long serialVersionUID = 1L;
    private String name;
    private String password;
    private String token;
    
    public User(String name, String password) {
        this.name = name;
        this.password = password;
        this.token = "";
    }
    
    @Override
    public String toString() {
        return "User [name=" + name + ", password=" + password + ", token=" + token + "]";
    }
}
