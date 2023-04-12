package com.ecolink.dev.server.services;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import com.ecolink.dev.server.domain.User;

public class ClientService {
    
    private User user;

    public void login( String username){
       //User user = new User(username, password);    
       //String response = "Welcome " + username; 
       System.out.println(username + " ClientService");
    }


}



