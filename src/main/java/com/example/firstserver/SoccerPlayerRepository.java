package com.example.firstserver;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface SoccerPlayerRepository extends MongoRepository<SoccerPlayer, String> {

    public SoccerPlayer findByFirstName(String FirstName);
    public SoccerPlayer findByLastName(String LastName);
    public List<SoccerPlayer> findByPosition(String Position);
    public SoccerPlayer insert(SoccerPlayer Player);
    public SoccerPlayer save(SoccerPlayer Player);
    public boolean existsByLastName(String lastName);

}
