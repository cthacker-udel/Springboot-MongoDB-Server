package com.example.firstserver;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import org.apache.coyote.Response;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/employees")
    public List<Employee> getEmployees(){

        List<Employee> employeeList = mongoTemplate.findAll(Employee.class);

        if(employeeList.size() == 0){
            System.out.println("\nNo Employees in Database\n");
            return null;
        }
        else{
            employeeList.stream().forEach(e -> System.out.println(e.toString()));
            return employeeList;
        }

    }

    @DeleteMapping("/employee/{name}")
    public ResponseEntity<Long> remove(@PathVariable String name){

            Criteria criteria = Criteria.where("Name").is(name);
            Query query = Query.query(criteria);
            DeleteResult result = mongoTemplate.remove(query,Employee.class);
            if(!result.wasAcknowledged()){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            else{
                return new ResponseEntity<>(HttpStatus.ACCEPTED);
            }

    }

    @DeleteMapping("/employee/all")
    public ResponseEntity<Long> removeAll(){

        DeleteResult result = mongoTemplate.remove(new Query(),"Employees");

        if(!result.wasAcknowledged()){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        else{
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }

    }

    @GetMapping("/employee/count")
    public Long getEmployeeCount(){

        long estimatedDocCount = mongoTemplate.getCollection("Employees").estimatedDocumentCount();

        System.out.println("\n-=-=-=-=\nCount : " + estimatedDocCount + "  \n-=-=-=-=");

        return estimatedDocCount;
    }

    @PutMapping("/employee/{employeeId}")
    public void updateEmployee(@RequestBody Employee employee, @PathVariable Integer employeeId){

        Criteria criteria = Criteria.where("ID").is(employeeId);

        employee.setId(employeeId);

        Query query = Query.query(criteria);

        Employee origEmployee = mongoTemplate.findOne(query,Employee.class);

        if(origEmployee == null){
            System.out.println("\nEmployee unable to be found\n");
        }
        else {
            mongoTemplate.getCollection("Employees").findOneAndReplace(origEmployee.pojoToDoc(), employee.pojoToDoc());
        }

    }






}
