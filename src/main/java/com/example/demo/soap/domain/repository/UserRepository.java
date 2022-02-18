package com.example.demo.soap.domain.repository;

import com.example.demo.soap.User;

public class UserRepository {

    //This is dummy User
    public User user = new User();
    public User findUser(long id) {
        return user;
    }
    //If this was a standalone project, we would connest this with database just as we did with the REST version.
    //Same goes with delete and save methods.
}
