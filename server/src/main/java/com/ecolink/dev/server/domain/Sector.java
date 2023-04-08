package com.ecolink.dev.server.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Sector implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String name;
	private String cep;
	private User sectorOwer;
//	private Poluition poluitionStatus;
}
