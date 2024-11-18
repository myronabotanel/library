package org.example.model.builder;

import org.example.model.Role;
import org.example.model.User;

import java.util.List;

public class UserBuilder {
    private User user;

    //pentru ca user are doar constructor fara parametrii, ca sa nu facem constructori cu toate combinatiile de parametrii
    public UserBuilder(){
        user = new User();
    }

    public UserBuilder setId(Long id){
        user.setId(id);
        return this;
    }

    public UserBuilder setUsername(String username){
        user.setUsername(username);
        return this;
    }

    public UserBuilder setPassword(String password){
        user.setPassword(password);
        return this;
    }

    public UserBuilder setRoles(List<Role> roles){
        user.setRoles(roles);
        return this;
    }

    public User build(){
        return user;
    }
}
