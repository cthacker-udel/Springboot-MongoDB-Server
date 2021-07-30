package com.example.firstserver;

import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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

    @PutMapping("/admin/{adminID}")
    public Object updateAdmin(@PathVariable("adminID") Integer adminID, @RequestBody Admin newAdmin){

        try{

            Criteria theCriteria = Criteria.where("ADMINID").is(adminID);
            Query query = Query.query(theCriteria);

            Admin origAdmin = mongoTemplate.findOne(query,Admin.class);

            assert origAdmin != null;
            mongoTemplate.getCollection("ADMIN").findOneAndReplace(origAdmin.pojoToDoc(),newAdmin.pojoToDoc());
            System.out.println("\n------- STATUS 200 -------\nADMIN HAS BEEN REPLACED\n");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }
        catch(Exception e){
            System.out.println("\n------- STATUS 400 -------\nINVALID REQUEST\n");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    @GetMapping("/admin/{adminId}")
    public Object getAdmin(@PathVariable("adminId") Integer adminId){

        try{

            Criteria criteria = Criteria.where("ADMINID").is(adminId);
            Query query = Query.query(criteria);
            Admin theAdmin = mongoTemplate.findOne(query,Admin.class);
            assert theAdmin != null;
            System.out.println(theAdmin.toString());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }
        catch(Exception e){

            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }

    }

    @DeleteMapping("/admin/{adminId}")
    public Object removeAdmin(@PathVariable("adminId") Integer adminId){

        try{

            Criteria criteria = Criteria.where("ADMINID").is(adminId);
            Query query = Query.query(criteria);
            mongoTemplate.findAndRemove(query,Admin.class);
            System.out.println("\n---- STATUS 200 ----\nAdmin successfully removed!");
            return new ResponseEntity<>(HttpStatus.ACCEPTED);

        }
        catch(Exception e){
            System.out.println("\n----- STATUS 400 -----\nUnable to remove admin");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/admin/count")
    public Long adminCount(){

        try{

            Long numAdmin = mongoTemplate.getCollection("ADMIN").estimatedDocumentCount();
            System.out.println("\n---- STATUS 200 ----\nADMIN COUNT : " + numAdmin + "\n");
            return numAdmin;

        }
        catch(Exception e){
            System.out.println("\n---- STATUS 400 ----\nUnable to count # of admin");
            return 0L;
        }

    }

    @DeleteMapping("/admin")
    public Object removeAllAdmin(){

        try{

            DeleteResult result = mongoTemplate.remove(new Query(),Admin.class);
            if(result.wasAcknowledged()){
                System.out.println("\n---- STATUS 200 ----\nAll Admin have been removed from collection");
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }
            else{
                System.out.println("\n---- STATUS 400 ----\nThe removal of all Admin has not been completed");
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }
        catch(Exception e){
            System.out.println("\n---- STATUS 400 ---\nUnable to remove all admin");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


}
