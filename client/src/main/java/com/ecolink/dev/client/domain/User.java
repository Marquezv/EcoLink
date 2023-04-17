package com.ecolink.dev.client.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Getter
	private String token;
	@Getter @Setter
	public String name;
	@Setter
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

    }    

}
