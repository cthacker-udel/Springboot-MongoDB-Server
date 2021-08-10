package com.example.firstserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    MongoTemplate tempate;

    @Autowired
    UserRepository repository;

    @PostMapping("/user")
    public Object addUser(@RequestBody User newUser){

        try{

            if(repository.existsByUserName(newUser.userName)){
                // user already exists
                return new ApiError(HttpStatus.CONFLICT,"Attempting to add already existing user","Invalid Request");
            }
            else{
                repository.insert(newUser);
                System.out.println("\nStatus 200 : Successfully added new user to database\n");
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

        }
        catch(Exception e){
            return new ApiError(HttpStatus.BAD_REQUEST,"Attempting to add user to database","Invalid request");
        }

    }



}
