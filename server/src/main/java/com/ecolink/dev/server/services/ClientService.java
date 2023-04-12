package com.ecolink.dev.server.services;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ecolink.dev.server.domain.User;

public class ClientService {
     
    private User user;
    private static List<User> userList = new ArrayList<>();
    
    
    public User login( String username, String password){
       loadList();
       User userFind = findUser(new User(username, password)).orElse(userList.get(0));
       
       if(checkCredentials(userFind)) return userFind;
       
       return userList.get(0);
       
    }

    private void loadList(){
        User user = new User("Erro", "1");
        User user2 = new User("Lua", "1");
        
        ClientService.userList.add(user);
        ClientService.userList.add(user2);
    }
    
    private boolean checkCredentials(User user) {
       return userList.stream()
          		.anyMatch(u -> u.getPassword().equals(user.getPassword()));
    } 
    
    private Optional<User> findUser(User user) {
    	Optional<User> results = userList.stream()
    			.filter(u -> u.getName().equals(user.getName())).findFirst();
    	
    	return results;
    }
    
}



