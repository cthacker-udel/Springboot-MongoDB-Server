package com.example.firstserver;


import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class ServerController {

    @Autowired
    MongoTemplate mongoTemplate;

    @GetMapping("admin/get/{collectionName}")
    public Object getCollection(@PathVariable String collectionName){

        try{
            MongoCollection<Document> theCollection = mongoTemplate.getCollection(collectionName);
            System.out.println("\n-------- RESPONSE 200 --------\nCollection successfully acquired!\n");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch(Exception e){

            System.out.println("\n-------- RESPONSE 400 --------\nCollection failed to be created!\n");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

    @PostMapping("/admin/create/{collectionName}")
    public ResponseEntity<Object> createCollection(@PathVariable String collectionName){

        try {
            MongoCollection<Document> newCollection = mongoTemplate.createCollection(collectionName);
            System.out.println("\n-------- RESPONSE 200 --------\nCollection successfully created!\n");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch(Exception e){
            System.out.println("\n-------- RESPONSE 400 --------\nCollection failed to be created!\n");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/admin/list/collections")
    public Object listCollections(){

        try{

            Set<String> collectionNames = mongoTemplate.getCollectionNames();
            System.out.println("\n-------- RESPONSE 200 --------\nCollection Names Successfully Acquired!\n");
            return collectionNames;

        }
        catch(Exception e){

            System.out.println("\n-------- RESPONSE 400 --------\nCollection Names failed to be acquired!\n");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }

}
