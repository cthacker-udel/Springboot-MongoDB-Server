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

import javax.validation.Valid;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RestController
public class AdminController {

    @Autowired
    MongoTemplate mongoTemplate;

    @PostMapping("/admin")
    public Object createAdmin(@Valid @RequestBody Admin newAdmin){

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
    public Object updateAdmin(@PathVariable("adminID") Integer adminID, @Valid @RequestBody Admin newAdmin){

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

    @GetMapping("/admin/names")
    public Object listAdminNames(){

        try{
            List<Admin> adminList = mongoTemplate.findAll(Admin.class);
            List<String> adminNames = new LinkedList<>();
            adminList.forEach(e -> adminNames.add(e.getName()));
            System.out.println("\n---- STATUS 200 ----\nList of Admin Names generated successfully!");
            return adminNames;
        }
        catch(Exception e){
            System.out.println("\n---- STATUS 400 ----\nUnable to list all admin names");
            return new LinkedList<>();
        }

    }

    @GetMapping("/admin/name/{Name}")
    public Object getAdminByName(@PathVariable("Name") String adminName){

        try{

            Criteria criteria = Criteria.where("Name").is(adminName);
            Query query = Query.query(criteria);
            List<Admin> admins = mongoTemplate.find(query,Admin.class);
            System.out.println("\n---- STATUS 200 ----\nListed all admin with name specified upon request");
            return admins;

        }
        catch(Exception e){
            System.out.println("\n---- Status 400 ----\nUnable to find admin by name");
            return new LinkedList<String>();
        }

    }


}
