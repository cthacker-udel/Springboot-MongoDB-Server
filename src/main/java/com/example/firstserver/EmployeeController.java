package com.example.firstserver;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {

    @Autowired
    MongoTemplate mongoTemplate;

    private static final String Name = "";
    private static final String Position = "";
    private static final String DOB = "";

    @PostMapping("/employee")
    public Employee makeEmployee(@RequestBody Employee employee){

        if(employee.getName().length() == 0 || employee.getPosition().length() == 0 || employee.getDOB().length() == 0){
            // parameters passed not valid
            System.out.println("\n--------- ERROR 401 ---------\nEmployee created is not valid : must supply proper fields --> name,position,dob");
            return null;
        }
        else{

            MongoCollection<Document> employeeCollection = mongoTemplate.getCollection("Employees");

            employeeCollection.insertOne(employee.pojoToDoc());

            System.out.println("\n-------- RESPONSE 200 --------\nEmployee created successfully!");

            return employee;

        }

    }

    @GetMapping("/employee")
    public Employee getEmployeeByName(@RequestParam(value="name", defaultValue = "defaultName") String name){

        MongoCollection<Document> employeeCollection = mongoTemplate.getCollection("Employees");

        if(name.equalsIgnoreCase("defaultName")){
            System.out.println("Failed to get Employee, supply name value when using get request");
            return null;
        }
        else{
            Criteria criteria = Criteria.where("Name").is(name);
            Query query = Query.query(criteria);
            Employee theEmployee = mongoTemplate.findOne(query,Employee.class);

            try {
                System.out.println(theEmployee.toString());
                return theEmployee;
            }
            catch(NullPointerException e){
                System.out.println("\nEmployee name not in database\n");
                return null;
            }
        }

    }




}
