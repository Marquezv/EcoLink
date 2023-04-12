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
    private static ArrayList<User> userList = new ArrayList<>();

    public void login( String username, String password){
    
       loadList();
       User user = new User(username, password);    
       checkCredentials(user);
    }

    private void loadList(){
        User user = new User("Vini", "senha");
        User user2 = new User("Lua", "senha");
        
        userList.add(user);
        userList.add(user2);
    }
    
    private boolean checkCredentials(User user) {
 
        for(User u: userList){
           if(user.name == u.name && user.password == u.password) return true;
        }
        return false;
   
    } 

}



