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

    public List<SoccerPlayer> findByDOB(String dateOfBirth);
    public List<SoccerPlayer> findByRedCards(Integer redCards);
    public List<SoccerPlayer> findByYellowCards(Integer yellowCards);

    public List<SoccerPlayer> findAll();

    public SoccerPlayer removeByLastName(String firstName);
    public SoccerPlayer removeByFirstName(String firstName);

    public Integer countByFirstName(String firstName);

}
