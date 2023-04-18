package com.ecolink.dev.server.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.ecolink.dev.server.domain.User;

public class ClientService {
     
    private User user;
    private static List<User> userList = new ArrayList<>();
    
    
    public User login( String username, String password){
       loadList();
       this.user = findUser(new User(username, password)).orElseThrow(IllegalStateException::new);
       
       if(checkCredentials(user)) return user;
       
       return user;
       
    }

    private void loadList(){
        User user = new User("Vini", "1");
        User user2 = new User("Lua", "1");
        User user3 = new User("Teste", "1");
        
        ClientService.userList.addAll(List.of(user, user2, user3));
    }
    
    private boolean checkCredentials(User user) {
       return userList.stream()
          		.anyMatch(u -> u.password.equals(user.password));
    } 
    
    private Optional<User> findUser(User user) {
    	Optional<User> results = userList.stream()
    			.filter(u -> u.name.equals(user.name)).findFirst();

    	return results;
    }
    
}

