package com.example.firstserver;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SoccerPlayerController {

    @Autowired
    SoccerPlayerRepository repository;

    @Autowired
    MongoTemplate mongoTemplate;

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

    @PutMapping("/soccer/name/last/{lastName}")
    public Object updateByLastName(@PathVariable("lastName") String lastName, @RequestBody SoccerPlayer player){

        SoccerPlayer result;
        if(!repository.existsByLastName(lastName)){
            // player does not exist
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid update request","Error : Player " + lastName + " does not exist");
        }
        else{
            result = repository.save(player);
            System.out.println(result.toString());
        }
        return result;

    }

    @PostMapping("/soccer")
    public Object addSoccerPlayer(@RequestBody SoccerPlayer player){


        try{
            repository.insert(player);
            return player;
        }
        catch(Exception e){
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid body format","The Soccer Player you tried inserting is not valid");
        }

    }

    @DeleteMapping("/soccer/removeAll")
    public Object removeAllSoccerPlayers(){

        try{
            DeleteResult result = mongoTemplate.remove(new Query(),SoccerPlayer.class);
            if(result.wasAcknowledged()) {
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        catch(Exception e){
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","400 : Invalid Request to remove all entities");
        }

    }

    @GetMapping("/soccer/date/{dateOfBirth}")
    public Object getByDOB(@PathVariable("dateOfBirth") String dateOfBirth){

        try{
            List<SoccerPlayer> players = repository.findByDOB(dateOfBirth);
            if(players == null){
                throw new Exception("Invalid request");
            }
            else{
                players.forEach(e -> System.out.println(e.toString()));
                return players;
            }
        }
        catch(Exception e){
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid DOB entry","There are no soccer players with that DOB in the database");
        }

    }

    @GetMapping("/soccer/red_cards/{redCards}")
    public Object getByRedCards(@PathVariable("redCards") Integer redCards){

        try{
            List<SoccerPlayer> players = repository.findByRedCards(redCards);
            if(players == null){
                throw new Exception("");
            }
            else{
                players.forEach(e -> System.out.println(e.toString()));
                return players;
            }
        }
        catch(Exception e){
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid request","Invalid request");
        }

    }

    @GetMapping("/soccer/yellow_cards/{yellowCards}")
    public Object getByYellowCards(@PathVariable("yellowCards") Integer yellowCards){

        try{
            List<SoccerPlayer> players = repository.findByYellowCards(yellowCards);
            players.stream().forEach(e -> System.out.println(e.toString()));
            return players;
        }
        catch(Exception e){
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Invalid amount sent");
        }

    }

    @GetMapping("soccer/position/{position}")
    public Object getByPosition(@PathVariable("position") String position){

        try{
            List<SoccerPlayer> players = repository.findByPosition(position);
            players.forEach(SoccerPlayer::toString);
            return players;
        }
        catch(Exception e){
            return new ApiError(HttpStatus.BAD_REQUEST,"Invalid Request","Invalid position sent");
        }

    }





}
