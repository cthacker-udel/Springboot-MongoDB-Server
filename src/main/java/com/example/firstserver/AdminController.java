package com.example.firstserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping("/admin")
    public Object createAdmin(@RequestBody Admin newAdmin){

        if(mongoTemplate.collectionExists("ADMIN")){

            try{
                mongoTemplate.getCollection("ADMIN").insertOne(newAdmin.pojoToDoc());

                System.out.println("\n--------- STATUS 200 ---------\nADMIN HAS BEEN CREATED!\n");

                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            catch(Exception e){
                System.out.println("\n--------- STATUS 400 ---------\nADMIN HAS NOT BEEN CREATED!\n");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }


        }
        else{
            System.out.println("\n--------- STATUS 400 ---------\nADMIN HAS NOT BEEN CREATED!\n");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/admin/all")
    public Object listAllAdmin(){

        try{

            List<Admin> admins = mongoTemplate.findAll(Admin.class);
            for(Admin eachAdmin : admins){

                System.out.println(eachAdmin.toString());

            }
            System.out.println("\n--------- STATUS 200 ---------\nADMINS HAVE BEEN LISTED!\n");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }
        catch(Exception e){

            System.out.println("\n--------- STATUS 400 ---------\nREQUEST ERROR!\n");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }

    }


}
