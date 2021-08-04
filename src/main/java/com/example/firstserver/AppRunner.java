package com.example.firstserver;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;


@Component
public class AppRunner implements CommandLineRunner {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    SoccerPlayerRepository repository;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n.... booting server");
        //System.out.println("inserting soccer player");
        //repository.insert(new SoccerPlayer("Lionel","Messi","12/1/1990","Striker",0,0));
    }
}


