package com.ecolink.dev.server.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

@Getter
@Setter
@ToString
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String token;
	public String name;
	public String password;
//	private LoginStatus loginStatus;
//	private Level level;
//	private Sector location;
//	private String office;

    public User(String name, String password){
        super();
        this.name = name;
        this.password = password;
        //this.location = location;
        //this.office = office;

        userToken();
    }
    
    private void userToken(){
       this.token = UUID.randomUUID().toString().substring(0, 5);
    }


}
