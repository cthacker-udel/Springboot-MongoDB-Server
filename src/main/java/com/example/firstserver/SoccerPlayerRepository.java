package com.example.firstserver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SoccerPlayerRepository extends MongoRepository<SoccerPlayer, String> {

    public SoccerPlayer findByFirstName(String firstName);
    public SoccerPlayer findByLastName(String lastName);
    public List<SoccerPlayer> findByPosition(String position);
    public Object insert(SoccerPlayer player);

}
