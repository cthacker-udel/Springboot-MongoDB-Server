package com.example.firstserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SoccerPlayerController {

    @Autowired
    SoccerPlayerRepository repository;

    @GetMapping("/soccer/name/first/{FirstName}")
    public Object getByFirstName(@PathVariable("FirstName") String FirstName){

        SoccerPlayer player = repository.findByFirstName(FirstName);
        if(player == null){
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid First Name","Error: First name " + FirstName + " does not exist in database");
        }
        return player;
    }

    @GetMapping("/soccer/name/last/{lastName}")
    public Object getByLastName(@PathVariable("lastName") String lastName){

        SoccerPlayer player = repository.findByLastName(lastName);
        if(player == null){
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Last Name","Error : Last name, " + lastName + " does not exist in database");
        }
        return player;
    }

}
