package com.example.firstserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ClientController {

    @Autowired
    ClientRepository repository;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/client/add")
    public Object addClient(@RequestBody Client client, @RequestHeader("Authorization") String secretKey){

        String hashedPass = new StringBuilder(secretKey).reverse().toString();

        if(userRepository.existsBySecretKey(hashedPass)){
            // valid user

            repository.saveClient(client);

            System.out.println("\n---STATUS 200 : USER SAVED IN DATABASE---\n");

            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }
        else{

            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","The secret key passed into the function header is invalid");

        }

    }

}
